package extrace.misc.model;

import java.util.Date;

public class Path {
    String userInfo;
    private int status;
    private Date start;
    private Date end;
    private String nodeName;
    private CustomerInfo receiver;

    public CustomerInfo getReceiver() {
        return receiver;
    }

    public void setReceiver(CustomerInfo receiver) {
        this.receiver = receiver;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public int getStatus() {
        return status;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getNodeName() {
        return nodeName;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public String toString() {
        return "Path{" +
                "userInfo='" + userInfo + '\'' +
                ", status=" + status +
                ", start=" + start +
                ", end=" + end +
                ", nodeName='" + nodeName + '\'' +
                ", receiver=" + receiver +
                '}';
    }
}
