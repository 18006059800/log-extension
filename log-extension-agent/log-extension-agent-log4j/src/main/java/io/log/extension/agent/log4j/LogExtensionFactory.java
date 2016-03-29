package io.log.extension.agent.log4j;

import org.slf4j.Logger;

public class LogExtensionFactory {

	private LogExtensionFactory() {

	}

	public LogExtension getLogger(Logger log) {
		LogExtension logExtension = new LogExtension(log);
		return logExtension;
	}
}
