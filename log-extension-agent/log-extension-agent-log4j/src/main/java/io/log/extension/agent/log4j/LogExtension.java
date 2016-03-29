package io.log.extension.agent.log4j;

import org.slf4j.Logger;

public class LogExtension {

	private Logger log;

	public LogExtension() {

	}

	public LogExtension(Logger log) {
		this.log = log;
	}

	public String getName() {
		return log.getName();
	}

	public void trace(String format, Object... arguments) {
		log.trace(format, arguments);
	}

	public void debug(String msg) {
		log.debug(msg);
	}

	public void debug(String format, Object... arguments) {
		log.debug(format, arguments);
	}

	public void info(String format, Object... arguments) {
		log.debug(format, arguments);
	}

	public void warn(String msg) {
		log.warn(msg);
	}

	public void warn(String format, Object... arguments) {
		log.warn(format, arguments);
	}

	public void error(String msg) {
		log.error(msg);
	}

	public void error(String format, Object... arguments) {
		log.error(format, arguments);
	}

}
