package banking;

public class AutoSaverRunnable implements Runnable
{
	AccountManager manager;
	
	public AutoSaverRunnable(AccountManager m)
	{
		manager = m;
	}
	
	@Override
	public void run()
	{
		while(!Thread.currentThread().isInterrupted())
		{
			try {
				Thread.sleep(5000);
				System.out.println("5초마다 자동저장됩니다.");	
				manager.SaveFileTXT();
			} catch (InterruptedException e) {
				//System.out.println(e);
				System.out.println("자동저장옵션을 끕니다.");
				return;
			} catch (Exception e) {
				//System.out.println(e);
				return;
			}
		}
	}
}
