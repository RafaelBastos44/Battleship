package View;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BoardPanel extends JPanel {
    private static final int GRID_SIZE = 15;
    private static final int CELL_SIZE = 30;
    private ShipPanel shipPanel;
    private Ship[][] grid;
    private Ship currentShip;
    private int currentRow, currentCol;

    public BoardPanel(ShipPanel shipPanel) {
        this.shipPanel = shipPanel;
        this.grid = new Ship[GRID_SIZE][GRID_SIZE];
        this.currentShip = null;
        this.currentRow = -1;
        this.currentCol = -1;

        setFocusable(true);
        requestFocusInWindow();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / CELL_SIZE;
                int row = e.getY() / CELL_SIZE;

                if (e.getButton() == MouseEvent.BUTTON1) { // Clique esquerdo
                    if (currentShip == null) { // Selecionar navio para posicionar
                        Ship selectedShip = shipPanel.getSelectedShip();
                        if (selectedShip != null) {
                            if (selectedShip instanceof Hidroaviao) {
                                currentShip = new Hidroaviao();
                                currentShip.isHorizontal = selectedShip.isHorizontal;
                            } else {
                                currentShip = new Ship(selectedShip.size, selectedShip.color); // Cria uma cópia do navio
                                currentShip.isHorizontal = selectedShip.isHorizontal;
                            }
                            if (canPlaceShip(currentShip, row, col)) {
                                placeShip(currentShip, row, col);
                                shipPanel.deselectShip(); // Deseleciona o navio no painel de navios
                                currentRow = row;
                                currentCol = col;
                                repaint();
                            }
                        }
                    } else { // Reposicionar navio já selecionado
                        if (canPlaceShip(currentShip, row, col)) {
                            removeShip(currentShip, currentRow, currentCol); // Remover navio da posição antiga
                            placeShip(currentShip, row, col); // Colocar navio na nova posição
                            currentRow = row;
                            currentCol = col;
                            repaint();
                        }
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3 && currentShip != null) { // Clique direito
                    removeShip(currentShip, currentRow, currentCol); // Remove navio antes de rotacionar
                    currentShip.rotate();
                    if (canPlaceShip(currentShip, currentRow, currentCol)) {
                        placeShip(currentShip, currentRow, currentCol); // Recoloca navio rotacionado
                    } else {
                        currentShip.rotate(); // Reverte rotação se não puder ser colocado
                        placeShip(currentShip, currentRow, currentCol);
                    }
                    repaint();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    currentShip = null;
                    currentRow = -1;
                    currentCol = -1;
                    repaint();
                }
            }
        });
    }

    private boolean canPlaceShip(Ship ship, int row, int col) {
        if (ship instanceof Hidroaviao) {
            // Lógica para o hidroavião
            if (ship.isHorizontal) {
                if (row + 1 >= GRID_SIZE || col - 1 < 0 || col + 1 >= GRID_SIZE) return false;
                return grid[row + 1][col - 1] == null &&
                       grid[row][col] == null &&
                       grid[row + 1][col + 1] == null;
            } else {
                if (row - 1 < 0 || row + 1 >= GRID_SIZE || col - 1 < 0) return false;
                return grid[row - 1][col - 1] == null &&
                       grid[row][col] == null &&
                       grid[row + 1][col - 1] == null;
            }
        } else {
            if (ship.isHorizontal) {
                if (col + ship.size > GRID_SIZE) {
                    return false; // Verifica se o navio cabe na grade horizontalmente
                }
                for (int i = 0; i < ship.size; i++) {
                    if (grid[row][col + i] != null) {
                        return false; // Verifica se há outro navio no caminho
                    }
                }
            } else {
                if (row + ship.size > GRID_SIZE) {
                    return false; // Verifica se o navio cabe na grade verticalmente
                }
                for (int i = 0; i < ship.size; i++) {
                    if (grid[row + i][col] != null) {
                        return false; // Verifica se há outro navio no caminho
                    }
                }
            }
        }
        return true;
    }

    private void placeShip(Ship ship, int row, int col) {
        if (ship instanceof Hidroaviao) {
            // Lógica especial para o navio especial
            if (ship.isHorizontal) {
                grid[row + 1][col - 1] = ship;
                grid[row][col] = ship;
                grid[row + 1][col + 1] = ship;
            } else {
                grid[row - 1][col - 1] = ship;
                grid[row][col] = ship;
                grid[row + 1][col - 1] = ship;
            }
        } else {
            if (ship.isHorizontal) {
                for (int i = 0; i < ship.size; i++) {
                    grid[row][col + i] = ship;
                }
            } else {
                for (int i = 0; i < ship.size; i++) {
                    grid[row + i][col] = ship;
                }
            }
        }
    }

    private void removeShip(Ship ship, int row, int col) {
        if (ship instanceof Hidroaviao) {
            // Lógica especial para o navio especial
            if (ship.isHorizontal) {
                grid[row + 1][col - 1] = null;
                grid[row][col] = null;
                grid[row + 1][col + 1] = null;
            } else {
                grid[row - 1][col - 1] = null;
                grid[row][col] = null;
                grid[row + 1][col - 1] = null;
            }
        } else {
            if (ship.isHorizontal) {
                for (int i = 0; i < ship.size; i++) {
                    grid[row][col + i] = null;
                }
            } else {
                for (int i = 0; i < ship.size; i++) {
                    grid[row + i][col] = null;
                }
            }
        }
    }

    public void printGrid() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col] != null) {
                    System.out.print(grid[row][col].getClass().getSimpleName().charAt(0) + " ");
                } else {
                    System.out.print("~ ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);

        // Desenhar a grade
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);

                if (grid[row][col] != null) {
                    g2d.setColor(grid[row][col].color);
                    g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    g2d.setColor(Color.BLUE);
                }
            }
        }

        // Desenhar o navio temporariamente posicionado
        if (currentShip != null && currentRow != -1 && currentCol != -1) {
            g2d.setColor(currentShip.color);
            if (currentShip instanceof Hidroaviao) {
                if (currentShip.isHorizontal) {
                    g2d.fillRect((currentCol - 1) * CELL_SIZE, (currentRow + 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    g2d.fillRect(currentCol * CELL_SIZE, currentRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    g2d.fillRect((currentCol + 1) * CELL_SIZE, (currentRow + 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else {
                    g2d.fillRect((currentCol - 1) * CELL_SIZE, (currentRow - 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    g2d.fillRect(currentCol * CELL_SIZE, currentRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    g2d.fillRect((currentCol - 1) * CELL_SIZE, (currentRow + 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            } else {
                if (currentShip.isHorizontal) {
                    for (int i = 0; i < currentShip.size; i++) {
                        g2d.fillRect(currentCol * CELL_SIZE + i * CELL_SIZE, currentRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                } else {
                    for (int i = 0; i < currentShip.size; i++) {
                        g2d.fillRect(currentCol * CELL_SIZE, currentRow * CELL_SIZE + i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
    }
}

