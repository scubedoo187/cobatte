import org.apache.log4j.Logger;

public class Log {
	/* Create Logger */
	static Logger logger = Logger.getLogger(Log.class.getName());
	/* log level : (debug > info > warn > error > fatal) */
		
	public void debug(String log) {	logger.debug("[D] " + log);	}
	
	public void info(String log) { logger.info("[I] " + log); }
	
	public void warn(String log) { logger.warn("[W] " + log); }
	
	public void error(String log) { logger.error("[E] " + log); }
	
	public void fatal(String log) { logger.fatal("[F] " + log); }
	
}
/* ���� 

* Logger ��ü ����. -> Logger l = Logger.getLogger(class��.class.getName());
* 
* �� �α׷������� �޼ҵ带 ȣ���Ͽ� ���ϴ� ���ο� �α׸� ����.
* 
* l.debug("���� �α� �޼���");
* l.info("���� �α� �޼���");
* l.warn("���� �α� �޼���");	
* l.error("���� �α� �޼���");
* l.fatal("���� �α� �޼���");
*  

*/