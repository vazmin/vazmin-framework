package com.github.vazmin.framework.web.support.model;

import com.github.vazmin.framework.web.support.annotation.Command;
import com.github.vazmin.framework.web.support.enu.ItemTypeEnum;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collector;

/**
 * 管理系统命令信息Bean，从 @Command 注解解析出代码、名称、路径等信息，构造 CommandInfo 对象。

 */
public class CommandInfo extends BaseInfo {
    private static final long serialVersionUID = 7291638968139425511L;
    /** 所属模块 */
    private ModuleInfo module;

    private String method;

    /** 请求方法 */
    private RequestMethod requestMethod;

    /** 是否是模块入口 */
    private boolean inlet;

    /** 是否在顶部导航的模块下级菜单显示 */
    private boolean showInMenu;

    /** 命令别名，当showInMenu为 true 时，在导航中显示此别名（一般设置为新增xxx）*/
    private String alias;

    /** 是否记录日志， true-记录，false-不记录，默认记录 */
    private boolean trace;

    public CommandInfo() {
    }

    public CommandInfo(Command command) {
        code = command.code();
        value = command.value();
        orderNumber = command.order();
        enable = command.enable();
        inlet = command.isInlet();
        showInMenu = command.showInMenu();
        alias = command.alias();
        icon = command.icon();
        trace = command.trace();
        discard = false;
        common = command.common();
    }

//    public boolean equals(CommandInfo o) {
//        return super.equals(o)
//                && Objects.equals(this.inlet, o.inlet)
//                && Objects.equals(this.showInMenu, o.showInMenu)
//                && Objects.equals(this.alias, o.alias)
//                && Objects.equals(this.trace, o.trace);
//    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }


    public ModuleInfo getModule() {
        return module;
    }

    public void setModule(ModuleInfo module) {
        this.module = module;
    }

    public boolean isInlet() {
        return inlet;
    }

    public void setInlet(boolean inlet) {
        this.inlet = inlet;
    }

    public boolean isShowInMenu() {
        return showInMenu;
    }

    public void setShowInMenu(boolean showInMenu) {
        this.showInMenu = showInMenu;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    public String getCommandKey() {
        return String.format("%s-%s", ItemTypeEnum.COMMAND.getValue(), id);
    }

    public String getParentKey() {
        return String.format("%s-%s", ItemTypeEnum.MODULE.getValue(), parentId);
    }

    public boolean isTrace() {
        return trace;
    }

    public void setTrace(boolean trace) {
        this.trace = trace;
    }

    public Key buildKey() {
        return new Key(getPath(), getRequestMethod());
    }

    public static class Key {
        private String path;

        private RequestMethod requestMethod;

        public Key() {
        }


        public Key(String path, RequestMethod requestMethod) {
            this.path = path;
            this.requestMethod = requestMethod;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public RequestMethod getRequestMethod() {
            return requestMethod;
        }

        public void setRequestMethod(RequestMethod requestMethod) {
            this.requestMethod = requestMethod;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return path.equals(key.path) &&
                    requestMethod == key.requestMethod;
        }

        @Override
        public int hashCode() {
            return Objects.hash(path, requestMethod);
        }
    }

    public boolean equalsDb(CommandInfo commandInfo) {
        return  equalsDbBase(commandInfo) &&
                method.equals(commandInfo.method) &&
                inlet == commandInfo.inlet;
    }
}
