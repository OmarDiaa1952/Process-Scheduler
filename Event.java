
public class Event
{
    private final String processName;
    private final float startTime;
    private float finishTime;
    
    public Event(String processName, float startTime, float finishTime)
    {
        this.processName = processName;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }
    
    public String getProcessName()
    {
        return processName;
    }
    
    public float getStartTime()
    {
        return startTime;
    }
    
    public float getFinishTime()
    {
        return finishTime;
    }
    
    public void setFinishTime(float finishTime)
    {
        this.finishTime = finishTime;
    }
}
