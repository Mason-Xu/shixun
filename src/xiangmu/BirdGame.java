package xiangmu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

    public class BirdGame extends JPanel {
	Bird bird;
	Column column1,column2;
	Ground ground;
	BufferedImage background;

	/**��ʼ��BirdGame �����Ա���*/
	public BirdGame() throws Exception{
		bird = new Bird();
		column1 = new Column(1);
		column2 = new Column(2);
		ground = new Ground();
		background = ImageIO.read(getClass().getResource("bg.jpg"));
	}
        /**BirdGame����ӷ���action()*/
        public void action() throws Exception{
			MouseListener l = new MouseAdapter(){
				//��갴��
				public void mousePressed(MouseEvent e){
					//��С�����Ϸ���
					bird.flappy();
				}
			};//�������¼�
			addMouseListener(l);
            while(true){
                ground.step();
                column1.step();
                column2.step();
				bird.step();
				bird.fly();
                repaint();
                Thread.sleep(1000/30);//һ��ˢ��30��
            }
        }
	/**��������ķ���*/
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		BirdGame game = new BirdGame();
		frame.add(game);
		frame.setSize(440, 670);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
        game.action();

	}
	/**��д���޸ģ�paint����ʵ�ֻ���*/
	public void paint (Graphics g){
		g.drawImage(background, 0, 0, null);
		g.drawImage(column1.image,column1.x-column1.width/2,column1.y-column1.height/2,null);
		g.drawImage(column2.image,column2.x-column2.width/2,column2.y-column2.height/2,null);
		g.drawImage(ground.image, ground.x, ground.y, null);
		//��ת(rotate)��ͼ����ϵ����API�ķ���
		Graphics2D g2 = (Graphics2D) g;
		g2.rotate(-bird.alpha,bird.x,bird.y);
		g.drawImage(bird.image,bird.x-bird.width/2,bird.y-bird.height/2,null);
		g2.rotate(bird.alpha,bird.x,bird.y);
	}
	/**����*/
	class Ground{
		BufferedImage image;
		int x,y;
		int width;
		int height;
		
		public Ground ()throws Exception{
			image=ImageIO.read(getClass().getResource("ground.jpg"));
			width = image.getWidth();
			height = image.getHeight();
			x = 0;
			y = 500;
		}
        //��������У���ӷ����������ƶ�һ��
        public void step(){
            x--;
            if(x == -109){
                x = 0;
            }
        }
	}
	/**�������ͣ�x��y�����ӵ����ĵ��λ��*/
	class Column{
		BufferedImage image;
		int x,y;
		int width;
		int height;
		int gap;//���Ӽ�ķ�϶
		int distance;//�������Ӽ�ľ���
		Random random =new Random();/**���������*/
		/**����������ʼ�����ݣ�n����ڼ�������*/
		public Column (int n) throws Exception{
			image = ImageIO.read(getClass().getResource("column.jpg"));
			width = image.getWidth();
			height = image.getHeight();
			gap = 144;
			distance = 245;
			x = 550 +(n-1)*distance;
			y = random.nextInt(218)+132;
		}
        /**��Column�����ᰢ��׷���step,��action�����е���*/
        public void step(){
            x--;
            if(x == -width/2){
                x = distance * 2 - width/2;
                y = random.nextInt(218)+132;
            }
        }

	}
	/**������ͣ�x,y��������͵�����λ��*/
	class Bird{
		BufferedImage image;
		int x,y;
		int width;
		int height;
		int size;//��Ĵ�С��������ײ���
        //��Bird�����������ԣ����ڼ������λ��
        double g;//�������ٶ�
        double t;//����λ�õ�ʱ����
        double v0;//��ʼ�����ٶ�
        double speed;//��ǰ�������ٶ�
        double s;//����ʱ��t���λ��
        double alpha;//�������� ���ȵ�λ

        //����һ��ͼƬ������Ķ���֡
        BufferedImage[] images;
        //���嶯��֡������±�λ��
        int index;



        public Bird ()throws Exception{
			image = ImageIO.read(getClass().getResource("0.jpg"));
			width = image.getWidth();
			height = image.getHeight();
			x = 132;
			y = 280;
			size = 40;

            g = 9.8;//������ٶ�
            v0 = 20;//�����ʼ�ٶ�
            t = 0.25;//����ʱ��
            speed = v0;
            s = 0;//λ��
            alpha = 0;//���

            //����һ�����飬�����˸�Ԫ��
            images = new BufferedImage[8];
            for(int  i = 0;i < 8;i++){
                images[i] = ImageIO.read(getClass().getResource(i+".jpg"));
            }
			index = 0;
		}
		//��Bird��������ӷ��裨fly���ķ���
		public void fly(){
			index++;
			image = images[(index/12)%8];
		}

        //��Bird���У��������ƶ�����
        public void step(){
            double v0 = speed;
            s = v0*t + t*t*g/2;
            y = y - (int)s;//�����������λ��
            double v = v0 - g * t;//�����´ε��ٶ�
            speed = v;

			//����JAVA API�ṩ�ķ����к������������
			alpha = Math.atan(s/8);
        }
		public void flappy(){
			//�������ó�ʼ�ٶȣ��������Ϸ�
			speed = v0;
		}
	}
}

