package analytics;

public class App {
	//private static final String V_21="21";
	private static final String V_12="12";

	public static void main(String[] args) throws Exception {
		
		
		Environment env = new Environment();
		CrawlerV12 wc = new CrawlerV12();
		
		env.setAeVersion(args[0]);
		env.setAeConnection(args[1]);
		env.setAwiUrl(args[2]);
		env.setClient(args[3]);
		env.setUser(args[4]);
		env.setDepartment(args[5]);
		env.setPassword(args[6]);
		env.setDashboard(args[7]);
		String widget = args[8];		
		env.setWidgets(widget.split(","));
		env.setFolderPath(args[9]);
		
		// getting Dashboard URL;
		if(V_12.equals(env.getAeVersion())) {
			
			String dashboadURL = wc.formAWI12DashboardUrl(env);
			env.setDashboadURL(dashboadURL);
		}
		System.out.println("Input Params:");
		System.out.println(env);
		
		wc.crawl(env);
		wc.close();

	}

}
