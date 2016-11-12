package xiangmu;

import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BirdGame extends JPanel{
       Bird bird;
       Column column1,column2;
       Ground ground;
       BufferedImage background;
	    /**��ʼ��birdgame�����Ա��� */ 
       public BirdGame() throws Exception{
    	       bird = new Bird();
    	       column1 = new Column(1);
    	       column2 = new Column(2);
    	       ground = new Ground();
    	       //background = ImageIO.read(getClass().getResource("bg,jpg"));
       }
	     
	    /**��������ķ���*/
	    public static void main(String[] args) throws Exception/*�׳��쳣*/ {
		    // TODO Auto-generated method stub
	    	JFrame frame = new JFrame();
	    	BirdGame game = new BirdGame();
	    	frame.add(game);
	    	frame.setSize(440,670);
	    	frame.setLocation(null);
	    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	frame.setVisible(true);
	    	
	    }/**����*/
	    class Ground{
	    	BufferedImage image;
	    	int x,y;
	    	int width;
	    	int height;
	    	
	    	public Ground() throws Exception{
	    		image = ImageIO.read(getClass().getResource("ground.jpg"));
    		    width = image.getWidth();
    		    height = image.getHeight();
    		    x = 0;
    		    y = 500;
	    	}
	    }
	    
	    
	    
	    /**�������ͣ�x,y�����ӵ����ĵ��λ��*/
	    class Column{
	    	BufferedImage image;
	    	int x,y;
	    	int width;
	    	int height;
	    	int gap;//���Ӽ�ķ�϶
	    	int distance;//�������Ӽ�ľ���
	    	Random random =new Random();
	    	/**����������ʼ�����ݣ�n����ڼ�������*/
	    	public Column(int n) throws Exception{
	    		image = ImageIO.read(getClass().getResource("column.jpg"));
	    		width = image.getWidth();
	    		height = image.getHeight();
	    		gap = 144;
	    		distance = 245;
	    		x = 550 +(n-1)*distance;
	    		y = random.nextInt(218)+132;
	    	}
	   }
	   /**������ͣ�x��y�������͵�����λ��*/
	    class Bird{
	    	BufferedImage image;
	    	int x,y;
	    	int width;
	    	int height;
	    	int size;//��Ĵ�С��������ײ���
	    	
	    	public Bird() throws Exception{
	    		image = ImageIO.read(getClass().getResource("0.jpg"));
	    		width = image.getWidth();
	    		height = image.getHeight();
	    		x = 132;
	    		y = 280;
	    		size = 40;
	    	}
	    }

}
