package View;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BoardPanel extends JPanel {
    private static final int GRID_SIZE = 15;
    private static final int CELL_SIZE = 30;
    private static final int PADDING = 20; // Padding for row/column labels
    private ShipPanel shipPanel;
    private Ship[][] playerOneGrid;
    private Ship[][] playerTwoGrid;
    private Ship[][] currentGrid;
    private Ship currentShip;
    private int currentRow, currentCol;
    private boolean isPlayerOneTurn;

    public BoardPanel(ShipPanel shipPanel) {
        this.shipPanel = shipPanel;
        this.playerOneGrid = new Ship[GRID_SIZE][GRID_SIZE];
        this.playerTwoGrid = new Ship[GRID_SIZE][GRID_SIZE];
        this.currentGrid = playerOneGrid;
        this.currentShip = null;
        this.currentRow = -1;
        this.currentCol = -1;
        this.isPlayerOneTurn = true;

        setFocusable(true);
        requestFocusInWindow();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = (e.getX() - PADDING) / CELL_SIZE;
                int row = (e.getY() - PADDING) / CELL_SIZE;

                if (col < 0 || col >= GRID_SIZE || row < 0 || row >= GRID_SIZE) {
                    return; // Click was outside of grid area
                }

                if (e.getButton() == MouseEvent.BUTTON1) { // Clique esquerdo
                    if (currentShip == null) { // Selecionar navio para posicionar
                        Ship selectedShip = shipPanel.getSelectedShip();
                        if (selectedShip != null) {
                            if (selectedShip instanceof Hidroaviao) {
                                currentShip = new Hidroaviao();
                                currentShip.isHorizontal = selectedShip.isHorizontal;
                            } else if (selectedShip instanceof Couracado) {
                                currentShip = new Couracado();
                                currentShip.isHorizontal = selectedShip.isHorizontal;
                            } else if (selectedShip instanceof Cruzador) {
                                currentShip = new Cruzador();
                                currentShip.isHorizontal = selectedShip.isHorizontal;
                            } else if (selectedShip instanceof Destroyer) {
                                currentShip = new Destroyer();
                                currentShip.isHorizontal = selectedShip.isHorizontal;
                            } else if (selectedShip instanceof Submarino) {
                                currentShip = new Submarino();
                                currentShip.isHorizontal = selectedShip.isHorizontal;
                            } else {
                                currentShip = new Ship(selectedShip.size, selectedShip.color); // Cria uma cópia do navio genérico
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
                    shipPanel.deselectShip(); // Deseleciona o navio no painel de navios
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
                return currentGrid[row + 1][col - 1] == null &&
                       currentGrid[row][col] == null &&
                       currentGrid[row + 1][col + 1] == null;
            } else {
                if (row - 1 < 0 || row + 1 >= GRID_SIZE || col - 1 < 0) return false;
                return currentGrid[row - 1][col - 1] == null &&
                       currentGrid[row][col] == null &&
                       currentGrid[row + 1][col - 1] == null;
            }
        } else {
            if (ship.isHorizontal) {
                if (col + ship.size > GRID_SIZE) {
                    return false; // Verifica se o navio cabe na grade horizontalmente
                }
                for (int i = 0; i < ship.size; i++) {
                    if (currentGrid[row][col + i] != null) {
                        return false; // Verifica se há outro navio no caminho
                    }
                }
            } else {
                if (row + ship.size > GRID_SIZE) {
                    return false; // Verifica se o navio cabe na grade verticalmente
                }
                for (int i = 0; i < ship.size; i++) {
                    if (currentGrid[row + i][col] != null) {
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
                currentGrid[row + 1][col - 1] = ship;
                currentGrid[row][col] = ship;
                currentGrid[row + 1][col + 1] = ship;
            } else {
                currentGrid[row - 1][col - 1] = ship;
                currentGrid[row][col] = ship;
                currentGrid[row + 1][col - 1] = ship;
            }
        } else {
            if (ship.isHorizontal) {
                for (int i = 0; i < ship.size; i++) {
                    currentGrid[row][col + i] = ship;
                }
            } else {
                for (int i = 0; i < ship.size; i++) {
                    currentGrid[row + i][col] = ship;
                }
            }
        }
    }

    private void removeShip(Ship ship, int row, int col) {
        if (ship instanceof Hidroaviao) {
            // Lógica especial para o navio especial
            if (ship.isHorizontal) {
                currentGrid[row + 1][col - 1] = null;
                currentGrid[row][col] = null;
                currentGrid[row + 1][col + 1] = null;
            } else {
                currentGrid[row - 1][col - 1] = null;
                currentGrid[row][col] = null;
                currentGrid[row + 1][col - 1] = null;
            }
        } else {
            if (ship.isHorizontal) {
                for (int i = 0; i < ship.size; i++) {
                    currentGrid[row][col + i] = null;
                }
            } else {
                for (int i = 0; i < ship.size; i++) {
                    currentGrid[row + i][col] = null;
                }
            }
        }
    }

    private String getRowLabel(int index) {
        return Character.toString((char) ('A' + index));
    }

    public void switchPlayer() {
        if (isPlayerOneTurn) {
            currentGrid = playerTwoGrid;
        } else {
            currentGrid = playerOneGrid;
        }
        isPlayerOneTurn = !isPlayerOneTurn;
        repaint();
    }

    public void clearGrid() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                currentGrid[row][col] = null;
            }
        }
        currentShip = null;
        currentRow = -1;
        currentCol = -1;
        repaint();
    }

    public void printGrid() {
        // Print column labels
        System.out.print("  ");
        for (int col = 1; col <= GRID_SIZE; col++) {
            System.out.print(col + " ");
        }
        System.out.println();

        for (int row = 0; row < GRID_SIZE; row++) {
            // Print row label
            System.out.print(getRowLabel(row) + " ");
            for (int col = 0; col < GRID_SIZE; col++) {
                if (currentGrid[row][col] != null) {
                    System.out.print(currentGrid[row][col].getSymbol() + " ");
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
                int x = PADDING + col * CELL_SIZE;
                int y = PADDING + row * CELL_SIZE;
                g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);

                if (currentGrid[row][col] != null) {
                    g2d.setColor(currentGrid[row][col].color);
                    g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    g2d.setColor(Color.BLUE);
                }
            }
        }

        // Desenhar as etiquetas de linha
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        for (int row = 0; row < GRID_SIZE; row++) {
            String label = getRowLabel(row);
            int labelX = 5;
            int labelY = PADDING + row * CELL_SIZE + CELL_SIZE / 2 + 5;
            g2d.drawString(label, labelX, labelY);
        }

        // Desenhar as etiquetas de coluna
        for (int col = 0; col < GRID_SIZE; col++) {
            String label = String.valueOf(col + 1);
            int labelX = PADDING + col * CELL_SIZE + CELL_SIZE / 2 - 5;
            int labelY = 15;
            g2d.drawString(label, labelX, labelY);
        }

        // Desenhar o navio temporariamente posicionado
        if (currentShip != null && currentRow != -1 && currentCol != -1) {
            g2d.setColor(currentShip.color);
            if (currentShip instanceof Hidroaviao) {
                if (currentShip.isHorizontal) {
                    g2d.fillRect(PADDING + (currentCol - 1) * CELL_SIZE, PADDING + (currentRow + 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    g2d.fillRect(PADDING + currentCol * CELL_SIZE, PADDING + currentRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    g2d.fillRect(PADDING + (currentCol + 1) * CELL_SIZE, PADDING + (currentRow + 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else {
                    g2d.fillRect(PADDING + (currentCol - 1) * CELL_SIZE, PADDING + (currentRow - 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    g2d.fillRect(PADDING + currentCol * CELL_SIZE, PADDING + currentRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    g2d.fillRect(PADDING + (currentCol - 1) * CELL_SIZE, PADDING + (currentRow + 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            } else {
                if (currentShip.isHorizontal) {
                    for (int i = 0; i < currentShip.size; i++) {
                        g2d.fillRect(PADDING + currentCol * CELL_SIZE + i * CELL_SIZE, PADDING + currentRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                } else {
                    for (int i = 0; i < currentShip.size; i++) {
                        g2d.fillRect(PADDING + currentCol * CELL_SIZE, PADDING + currentRow * CELL_SIZE + i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PADDING + GRID_SIZE * CELL_SIZE, PADDING + GRID_SIZE * CELL_SIZE);
    }
}
