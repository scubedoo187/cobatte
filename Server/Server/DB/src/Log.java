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
/* 사용법 

* Logger 객체 생성. -> Logger l = Logger.getLogger(class명.class.getName());
* 
* 각 로그레벨명의 메소드를 호출하여 원하는 라인에 로그를 남김.
* 
* l.debug("남길 로그 메세지");
* l.info("남길 로그 메세지");
* l.warn("남길 로그 메세지");	
* l.error("남길 로그 메세지");
* l.fatal("남길 로그 메세지");
*  

*/