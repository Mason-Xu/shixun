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
	    /**初始化birdgame的属性变量 */ 
       public BirdGame() throws Exception{
    	       bird = new Bird();
    	       column1 = new Column(1);
    	       column2 = new Column(2);
    	       ground = new Ground();
    	       //background = ImageIO.read(getClass().getResource("bg,jpg"));
       }
	     
	    /**启动软件的方法*/
	    public static void main(String[] args) throws Exception/*抛出异常*/ {
		    // TODO Auto-generated method stub
	    	JFrame frame = new JFrame();
	    	BirdGame game = new BirdGame();
	    	frame.add(game);
	    	frame.setSize(440,670);
	    	frame.setLocation(null);
	    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	frame.setVisible(true);
	    	
	    }/**地面*/
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
	    
	    
	    
	    /**柱子类型，x,y是柱子的中心点的位置*/
	    class Column{
	    	BufferedImage image;
	    	int x,y;
	    	int width;
	    	int height;
	    	int gap;//柱子间的缝隙
	    	int distance;//两个柱子间的距离
	    	Random random =new Random();
	    	/**构造器：初始化数据，n代表第几根柱子*/
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
	   /**鸟的类型，x，y是鸟类型的中心位置*/
	    class Bird{
	    	BufferedImage image;
	    	int x,y;
	    	int width;
	    	int height;
	    	int size;//鸟的大小，用于碰撞检测
	    	
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
