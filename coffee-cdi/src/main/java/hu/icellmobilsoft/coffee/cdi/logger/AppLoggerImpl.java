/*-
 * #%L
 * Coffee
 * %%
 * Copyright (C) 2020 i-Cell Mobilsoft Zrt.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package hu.icellmobilsoft.coffee.cdi.logger;

import java.text.MessageFormat;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import hu.icellmobilsoft.coffee.cdi.logger.LogContainer.LogLevel;
import hu.icellmobilsoft.coffee.se.logging.JulLevel;

/**
 * <p>
 * AppLoggerImpl class.
 * </p>
 *
 * @author ischeffer
 * @since 1.0.0
 */
@Named
@Dependent
@DefaultAppLogger
public class AppLoggerImpl implements AppLogger {

    private static final long serialVersionUID = 1L;

    private Logger logger;

    @Inject
    private LogContainer logContainer;

    /** {@inheritDoc} */
    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /** {@inheritDoc} */
    @Override
    public Logger getLogger() {
        return logger;
    }

    private Logger logger() {
        if (getLogger() == null) {
            // hogyha null lenne a logger (nem allitja be valaki amikor kell)
            // Logger log = CommonLoggerFactory.getLogger(getClass());
            // Logger log = Logger.getLogger(getClass());
            Logger log = Logger.getLogger(getClass().getName());
            log.log(JulLevel.WARN, "Logger not SET! Applogger create temporary logger!");
            return log;
        } else {
            return logger;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void trace(String msg) {
        logger().log(JulLevel.TRACE, msg);
        logContainer.trace(msg);
    }

    /** {@inheritDoc} */
    @Override
    public void trace(String format, Object... arguments) {
        logger().log(JulLevel.TRACE, () -> MessageFormat.format(format, arguments));
        logContainer.trace(format, arguments);
    }

    /** {@inheritDoc} */
    @Override
    public void trace(String msg, Throwable t) {
        logger().log(JulLevel.TRACE, msg, t);
        logContainer.trace(msg, t);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isTraceEnabled() {
        return logger().isLoggable(JulLevel.TRACE);
    }

    /** {@inheritDoc} */
    @Override
    public void debug(String msg) {
        logger().log(JulLevel.DEBUG, msg);
        logContainer.debug(msg);
    }

    /** {@inheritDoc} */
    @Override
    public void debug(String format, Object... arguments) {
        logger().log(JulLevel.DEBUG, () -> MessageFormat.format(format, arguments));
        logContainer.debug(format, arguments);
    }

    /** {@inheritDoc} */
    @Override
    public void debug(String msg, Throwable t) {
        logger().log(JulLevel.DEBUG, msg, t);
        logContainer.debug(msg, t);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isDebugEnabled() {
        return logger().isLoggable(JulLevel.DEBUG);
    }

    /** {@inheritDoc} */
    @Override
    public void info(String msg) {
        logger().log(JulLevel.INFO, msg);
        logContainer.info(msg);
    }

    /** {@inheritDoc} */
    @Override
    public void info(String format, Object... arguments) {
        logger().log(JulLevel.INFO, () -> MessageFormat.format(format, arguments));
        logContainer.info(format, arguments);
    }

    /** {@inheritDoc} */
    @Override
    public void info(String msg, Throwable t) {
        logger().log(JulLevel.INFO, msg, t);
        logContainer.info(msg, t);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isInfoEnabled() {
        return logger().isLoggable(JulLevel.INFO);
    }

    /** {@inheritDoc} */
    @Override
    public void warn(String msg) {
        logger().log(JulLevel.WARN, msg);
        logContainer.warn(msg);
    }

    /** {@inheritDoc} */
    @Override
    public void warn(String format, Object... arguments) {
        logger().log(JulLevel.WARN, () -> MessageFormat.format(format, arguments));
        logContainer.warn(format, arguments);
    }

    /** {@inheritDoc} */
    @Override
    public void warn(String msg, Throwable t) {
        logger().log(JulLevel.WARN, msg, t);
        logContainer.warn(msg, t);
    }

    /** {@inheritDoc} */
    @Override
    public void error(String msg) {
        logger().log(JulLevel.ERROR, msg);
        logContainer.error(msg);
    }

    /** {@inheritDoc} */
    @Override
    public void error(String format, Object... arguments) {
        logger().log(JulLevel.ERROR, () -> MessageFormat.format(format, arguments));
        logContainer.error(format, arguments);
    }

    /** {@inheritDoc} */
    @Override
    public void error(String msg, Throwable t) {
        logger().log(JulLevel.ERROR, msg, t);
        logContainer.error(msg, t);
    }

    /**
     * {@inheritDoc}
     *
     * request szinten eltarolt valtozo elkerese
     */
    public Object getValue(String key) {
        return logContainer.getValue(key);
    }

    /**
     * {@inheritDoc}
     *
     * request szinten eltarolt valtozo elmentese
     */
    public void setValue(String key, Object value) {
        logContainer.setValue(key, value);
    }

    /**
     * {@inheritDoc}
     *
     * request szinten eltarolt valtozo torlese
     */
    public void removeValue(String key) {
        logContainer.removeValue(key);
    }

    /**
     * <p>
     * writeLogToInfo.
     * </p>
     */
    public void writeLogToInfo() {
        logger().log(JulLevel.INFO, toString());
    }

    /**
     * <p>
     * writeLogToError.
     * </p>
     */
    public void writeLogToError() {
        logger().log(JulLevel.ERROR, toString());
    }

    /** {@inheritDoc} */
    @Override
    public void writeLog() {
        LogLevel logLevel = logContainer.getHighestLogLevel();
        switch (logLevel) {
        case CUSTOM:
        case TRACE:
            logger().log(JulLevel.TRACE, toString());
            break;
        case DEBUG:
            logger().log(JulLevel.DEBUG, toString());
            break;
        case INFO:
            logger().log(JulLevel.INFO, toString());
            break;
        case WARN:
            logger().log(JulLevel.WARN, toString());
            break;
        case ERROR:
            logger().log(JulLevel.ERROR, toString());
            break;
        default:
            logger().log(JulLevel.ERROR, toString());
            break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String proxy = logContainer.getClass() + "@" + logContainer.hashCode() + "@@" + System.identityHashCode(logContainer);
        String clazz = logContainer.getClass().getSuperclass() + "@" + logContainer.getClass().getSuperclass().hashCode();
        return proxy + " -> " + clazz + ":\n" + logContainer.toString();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isThereAnyError() {
        return (logContainer.getHighestLogLevel() == LogLevel.ERROR);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isThereAnyWarning() {
        return (logContainer.getHighestLogLevel() == LogLevel.WARN || logContainer.getHighestLogLevel() == LogLevel.ERROR);
    }

}
