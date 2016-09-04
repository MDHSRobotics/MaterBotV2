/**
 * 
 */
package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

/**
 * @author maps
 *
 */
public class LogNotification extends Notification {
	public enum Level{
		INFO,
		WARN,
		ERROR
	};
	
	private String message;
	public String getMessage() {
		return message;
	}
	
	private String source;
	public String getSource() {
		return source;
	}
	
	private Level level;
	public Level getLevel(){
		return level;
	}
	
	/**
	 * @param messageId
	 */

	public LogNotification(Level level, String source,String message) {
		this(level,source,message,false);
	}

	public LogNotification(Level level, String source,String message,boolean showJavaConsole) {
		super("LogNotification",showJavaConsole);
		this.message = message;
		this.source = source;
		this.level = level;
	}	

	/* (non-Javadoc)
	 * @see robonotifications.Notification#toJSON()
	 */
	protected void addJSONPayload() {
		if(getLevel()!=null){
			if(sb.length()>1) sb.append(',');
			sb.append(String.format("\"level\":\"%s\"",getLevel().toString()));
		}
		if(getSource()!=null){
			if(sb.length()>1) sb.append(',');
			sb.append(String.format("\"source\":\"%s\"",getSource()));
		}
		if(getMessage()!=null){
			if(sb.length()>1) sb.append(',');
			sb.append(String.format("\"message\":\"%s\"",getMessage()));
		}
	}

	public Notification parse(String notification) {
		return null;
	}


}