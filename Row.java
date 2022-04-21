
public class Row
{
    private String processName;
    private float arrivalTime;
    private float burstTime;
    private float priorityLevel;
    private float waitingTime;
    private float turnaroundTime;
    
    private Row(String processName, float arrivalTime, float burstTime, float priorityLevel, float waitingTime, float turnaroundTime)
    {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityLevel = priorityLevel;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnaroundTime;
    }
    
    public Row(String processName, float arrivalTime, float burstTime, float priorityLevel)
    {
        this(processName, arrivalTime, burstTime, priorityLevel, 0, 0);
    }
    
    public Row(String processName, float arrivalTime, float burstTime)
    {
        this(processName, arrivalTime, burstTime, 0, 0, 0);
    }
    
    public void setBurstTime(float burstTime)
    {
        this.burstTime = burstTime;
    }
    
    public void setWaitingTime(float waitingTime)
    {
        this.waitingTime = waitingTime;
    }
    
    public void setTurnaroundTime(float turnaroundTime)
    {
        this.turnaroundTime = turnaroundTime;
    }
    
    public String getProcessName()
    {
        return this.processName;
    }
    
    public float getArrivalTime()
    {
        return this.arrivalTime;
    }
    
    public float getBurstTime()
    {
        return this.burstTime;
    }
    
    public float getPriorityLevel()
    {
        return this.priorityLevel;
    }
    
    public float getWaitingTime()
    {
        return this.waitingTime;
    }
    
    public float getTurnaroundTime()
    {
        return this.turnaroundTime;
    }
}
