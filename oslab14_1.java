import java.lang.Thread;
public class oslab14_1
{
	public static int c=0;
	static class add implements Runnable
	{
		public void run()
		{
			for(int i=0;i<2500000;i++)
			{
				count(1);
			}
		}
	}
	static class sub implements Runnable
	{
		public void run()
		{
			for(int i=0;i<2500000;i++)
			{
				count(-1);
			}
		}
	}
	static synchronized public void count(int n)
	{
		c+=n;
	}
	public static void main(String[] args)
	{
		add a = new add();
		sub b = new sub();
		Thread t1 = new Thread(a);
		Thread t2 = new Thread(a);
		Thread t3 = new Thread(b);
		Thread t4 = new Thread(b);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		try
		{
			t1.join();
			t2.join();
			t3.join();
			t4.join();
		}
		catch(InterruptedException e)
		{;}
		System.out.println(c);
	}
}
