import java.util.concurrent.locks.*;
import java.lang.Thread;
public class oslab14_2
{
	private static Lock lock = new ReentrantLock();
	private static Condition cCond = lock.newCondition();
	private static Condition pCond = lock.newCondition();
	private static int product = 0;
	static class Consumer implements Runnable
	{
		public void run()
		{
			lock.lock();
			try
			{
				while(true)
				{
					lock.lock();
					if(product == 0)
					{
						System.out.println("EMPTY ");
						cCond.await();
					}
					product -= 1;
					System.out.println("\tCONSUMED: "+product);
					pCond.signal();
					lock.unlock();
				}
			}
			catch(InterruptedException e){e.printStackTrace();}
		}
	}
	static class Producer implements Runnable
	{
		public void run()
		{
			lock.lock();
			try
			{
				while(true)
				{
					lock.lock();
					if(product == 10)
					{
						System.out.println("FULL ");
						pCond.await();
					}
					product += 1;
					System.out.println("Produced: "+product);
					cCond.signal();
					lock.unlock();
				}
			}
			catch(InterruptedException e){e.printStackTrace();}
		}
	}
	public static void main(String[] args)
	{
		Consumer c = new Consumer();
		Producer p = new Producer();
		Thread t0 = new Thread(c);
		Thread t1 = new Thread(p);
		t0.start();
		t1.start();
		try
		{
			t0.join();
			t1.join();
		}
		catch(InterruptedException e){e.printStackTrace();}
	}
}
