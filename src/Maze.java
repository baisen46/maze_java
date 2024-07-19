
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Maze implements KeyListener {

    private int pointX;
    private int pointY;
    private int width;
    private int height;
    private byte[][] map;
    private Random random = new Random();

    public Maze(int w, int h) {
        width = w;
        height = h;
        if (isValidDimensions(w, h)) {
            map = new byte[width][height];
            make();
        } else {
            System.out.println("縦・横共に5以上の奇数で作成してください。");
        }
    }

    private boolean isValidDimensions(int w, int h) {
        return w % 2 != 0 && h % 2 != 0 && w >= 5 && h >= 5;
    }

    int randomPos(int muki) {
        return 1 + 2 * random.nextInt((muki - 1) / 2);
    }

    private void make() {
        pointX = randomPos(width);
        pointY = randomPos(height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[x][y] = 1;
            }
        }
        map[pointX][pointY] = 0;
        dig(pointX, pointY);
    }

    private void dig(int x, int y) {
        int[] directions = {0, 1, 2, 3};
        shuffleArray(directions);

        for (int direction : directions) {
            int dx = 0, dy = 0;
            switch (direction) {
                case 0: dy = -2; break; // 上
                case 1: dy = 2; break;  // 下
                case 2: dx = -2; break; // 左
                case 3: dx = 2; break;  // 右
            }

            int nx = x + dx;
            int ny = y + dy;

            if (isInBounds(nx, ny) && map[nx][ny] == 1) {
                map[x + dx / 2][y + dy / 2] = 0;
                map[nx][ny] = 0;
                dig(nx, ny);
            }
        }
    }

    private boolean isInBounds(int x, int y) {
        return x > 0 && x < width - 1 && y > 0 && y < height - 1;
    }

    private void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public void show() {
        for (int y = 0; y < height; y++) {
            System.out.println();
            for (int x = 0; x < width; x++) {
                System.out.print(map[x][y] == 1 ? "##" : "  ");
            }
        }
    }

    public byte[][] getMaze() {
        return map;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("スペースキーが押されました。迷路を再生成します。");
            make();
            show();
            MainMaze.spacePressed = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

