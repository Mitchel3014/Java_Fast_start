import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Catch_the_drop extends JFrame {

    private static Catch_the_drop game_window;//Вводим переменную "game_window" класса названия нашей игры для отрисовки игрового окна
    private static long last_frame_time;//Задаем переменную для подсчета времени отрисовки между кадрами
    private static Image background;
    private static Image game_over;
    private static Image drop;
    private static float drop_left = 200;
    private static float drop_top = -100;
    private static float drop_v = 200;
    private static int score;//Переменная для подсчета очков

    public static void main(String[] args) throws IOException {
        //Инициализация переменных для  отрисовки наших картинок
        background = ImageIO.read(Catch_the_drop.class.getResourceAsStream("background.png"));
        game_over = ImageIO.read(Catch_the_drop.class.getResourceAsStream("game_over.png"));
        drop = ImageIO.read(Catch_the_drop.class.getResourceAsStream("drop.png"));

        game_window = new Catch_the_drop();//Инициализируем переменную "game_window" класса названия нашей игры для отрисовки игрового окна
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(200, 100);
        game_window.setSize(906, 478);
        game_window.setResizable(false);
        last_frame_time = System.nanoTime();//Задаем значение переменной для подсчета времени отрисовки. Возвращаем текущее время в нано-секундах
        GameFiled game_field = new GameFiled();//Создание нового объекта GameField для отрисовки панели
        game_field.addMouseListener(new MouseAdapter() {//Добавляем метод для отслеживания нажатия клавиш мыши
            @Override
            public void mousePressed(MouseEvent e) {
                //Получаем координаты мыши
                int x = e.getX();
                int y = e.getY();
                float drop_right = drop_left+drop.getWidth(null);//Получаем правую границу капли, приавляя к левой границе ширину самой капли
                float drop_bottom = drop_top+drop.getHeight(null);//Получаем нижнюю границу капли, прибавляя к верхней границе высоту самой капли

                boolean is_drop = x>= drop_left && x<= drop_right && y>= drop_top && x<= drop_bottom;//Описываем условие, когда мы попали внутрь капли (т.е. мышка находится в границах параметров капли)

                //Описываем действия, выполняющиеся при условии попадания на каплю
                if (is_drop == true) {
                    drop_top = -100;//Откинули каплю обратно по вертикали
                    drop_left = (int) (Math.random() * (game_field.getWidth() - drop.getWidth(null)));//Откидываем каплю в рандомное место в рамках игрового поля, указвыая, что генерируем случайную позицию от 0 до Размер игрового поля за вычетом ширины капли, чтобы не попасть за пределы игрового поля
                    drop_v = drop_v + 10;//увеличиваем скорость капли
                    score++;//Считаем очки
                    game_window.setTitle("Ваш счет: " +score);//Выводим очки
                }
            }
        });
        game_window.add(game_field);//Добавление панели в наше оконо
        game_window.setVisible(true);
    }

    private static void onRepaint (Graphics g) {
        long current_time = System.nanoTime();//Получаем текущее время
        float delta_time = (current_time - last_frame_time)* 0.000000001f;//Подсчитываем дельту-времени для отрисовки (период)
        last_frame_time = current_time;

        drop_top = drop_top + drop_v * delta_time;//Отрисовка смещения капли сверху вниз с учетом ее скорости и периода обновления кадров
        //drop_left = drop_left + drop_v * delta_time;//Отрисовка смещения капли по диагонали с учетом ее скорости и периода обновления кадров


        //Отрисовка наших картинок
        g.drawImage(background, 0, 0, null);
        g.drawImage(drop, (int) drop_left, (int) drop_top, null);
        if (drop_top > game_window.getHeight()) {
            g.drawImage(game_over, 280, 120, null);//Если верхняя граница капли ниже (т.е. по координатам Y больше нижней граница окна) нижней границы окна, выводим "Game over"
        }

        //g.fillOval(10, 10, 200, 100);//Отрисовка овала с помощью компонента g на панели
        //g.drawLine(500,300, 50, 300);//Отрисовка линии с помощью компонента g на панели
    }

    private static class GameFiled extends JPanel {//Создание класса для отрисовки панели для отображения
        @Override
        protected void paintComponent (Graphics g) {//Переопределение метода paintComponent для отрисовки панели
            super.paintComponent(g);//Получаем доступ к родительскому методу paintComponent для отрисовки панели и передача объекта класса Graphics, с помощью которого она рисуется
            onRepaint(g);//Вызов метода onRepaint в котором мы указываем, что именно нам нужно отрисовать на панели
            repaint();//Увеличение частоты отрисовки панели
        }
    }
}
