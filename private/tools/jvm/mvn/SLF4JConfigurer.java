package tools.jvm.mvn;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.*;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.*;
import ch.qos.logback.core.spi.ContextAwareBase;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class SLF4JConfigurer extends ContextAwareBase implements Configurator {
    private static ToolLogLevel root = ToolLogLevel.OFF;

    public synchronized static ToolLogLevel getLogLevel() {
        return root;
    }

    private synchronized static void setLogLevel(ToolLogLevel l) {
        root = l;
    }

    enum ToolLogLevel {
        OFF("off"),

        /**
         * print maven default output + info messages by a tool;
         */
        INFO("info"),

        /**
         * print maven default output + debug messages by a tool (for example generate pom);
         */
        DEBUG("debug"),

        /**
         * print maven debug output + debug messages by a tool;
         */
        TRACE("trace");

        private final String type;

        ToolLogLevel(String type) {
            this.type = type;
        }

        static Optional<ToolLogLevel> find(String name) {
            for (ToolLogLevel l : ToolLogLevel.values()) {
                if (l.type.equalsIgnoreCase(name)) return Optional.of(l);
            }
            return Optional.empty();
        }
    }

    @Override
    public void configure(LoggerContext logCtx) {
        String logLvl = System.getProperty("tools.jvm.mvn.LogLevel");
        ToolLogLevel logLevel = ToolLogLevel.find(logLvl).orElse(ToolLogLevel.OFF);
        System.out.println("tools.jvm.mvn.LogLevel=" + logLevel);
        setLogLevel(logLevel);
        initLoggers(logCtx, logLevel);
    }

    private void initLoggers(LoggerContext logCtx, ToolLogLevel logLevel) {
        PatternLayoutEncoder logEncoder = new PatternLayoutEncoder();
        logEncoder.setContext(logCtx);
        logEncoder.setPattern("%d{HH:mm:ss.SSS} %-5level [class=%logger{0}] - %msg%n");
        logEncoder.start();

        ConsoleAppender<ILoggingEvent> logConsoleAppender = new ConsoleAppender<>();
        logConsoleAppender.setContext(logCtx);
        logConsoleAppender.setName("STDOUT");
        logConsoleAppender.setEncoder(logEncoder);
        logConsoleAppender.start();

        Logger rootLog = logCtx.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLog.addAppender(logConsoleAppender);

        setLoggers(logLevel, rootLog);
    }

    private static void setLoggers(ToolLogLevel logLevel, Logger toolLog) {
        switch (logLevel) {
            case OFF:
            case INFO:
                toolLog.setLevel(Level.INFO);
                break;
            case DEBUG:
            case TRACE:
                toolLog.setLevel(Level.DEBUG);
                break;
        }
    }
}