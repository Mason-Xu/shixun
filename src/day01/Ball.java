package day01;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//导入Frame

public class Ball {

	/**
	 * @param args
	 */
	
	
	           public static void main(String[] args) {
	                 // TODO Auto-generated method stub
	            	//String s = new String("HELLO WORLD");
			       //System.out.println(s);
			       //System.out.println();
			    Frame w = new Frame();

				w.setSize(1024, 768);

//				w.setVisible(true);

				
				w.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e){
						System.exit(0);
					}
				});
				w.setSize(1080, 820);
				MyPanel mp =  new  MyPanel();
				w.setBackground(Color.white);
				w.add(mp);
				w.setVisible(true);//设置窗口可见
				Thread t = new Thread(mp);
				t.start();
        		w.show();

	}

}
class MyPanel extends Panel implements Runnable{//多线程处理
	    int x = 30;
		int y = 30;
		int i1 = 1;
		int i2 = 1;
	public void paint(Graphics g){
		
		g.setColor(Color.red);
	    g.fillOval(x, y, 60, 60);
		g.fillRect(x,y,40,40);
	    
	    
	    
		
		}
	public void run(){
		    while(true){
		    	x = x+i1;
		    	y = y+i2;
		    	repaint();
		    	if(x==1000){
		    		i1=-1;
		    	}
		    	if(x==0){
		    		i1=1;
		    	}
		    	if(y==700){
		    		i2=-1;
		    	}
		    	if(y==0){
		    		i2=1;
		    	}
		    	/*public void a(){
		    		while(!(y>300||x>270)){
		    			x++;
		    			y++;
		    			try{
		    				Thread.sleep(20);
		    			}catch(Exception e){}
		    			repaint();
		    		}
		    		if(x==270){
		    			c();
		    		}
		    		if(y==400)
		    			b();
		    	}
		    }	*/
			    
	            try{
	    	          Thread.sleep(1);
	           }catch(Exception e){}
	                 repaint();
	     }
	 }	   
}

