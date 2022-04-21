
import java.util.Collections;
import java.util.List;

public class FirstComeFirstServe extends CPUScheduler
{
    @Override
    public void process()
    {        
        Collections.sort(this.getRows(), (Object o1, Object o2) -> {
            if (((Row) o1).getArrivalTime() == ((Row) o2).getArrivalTime())
            {
                return 0;
            }
            else if (((Row) o1).getArrivalTime() < ((Row) o2).getArrivalTime())
            {
                return -1;
            }
            else
            {
                return 1;
            }
        });
        
        List<Event> timeline = this.getTimeline();
        float time=0;
        for (Row row : this.getRows())
        {
            /*if (timeline.isEmpty())
            {
                timeline.add(new Event(row.getProcessName(), row.getArrivalTime(), row.getArrivalTime() + row.getBurstTime()));
            }
            else
            {
                Event event = timeline.get(timeline.size() - 1);
                timeline.add(new Event(row.getProcessName(), event.getFinishTime(), event.getFinishTime() + row.getBurstTime()));
            }*/
            if(row.getArrivalTime()>time){//IDLE
                timeline.add(new Event("I", time, row.getArrivalTime()+time));
                time=row.getArrivalTime();
            }
            timeline.add(new Event(row.getProcessName(), time, time+row.getBurstTime()));
            time+=row.getBurstTime();
            
        }
        
        for (Row row : this.getRows())
        {
            if("I".equals(row.getProcessName())) continue;
            row.setWaitingTime((this.getEvent(row).getStartTime() - row.getArrivalTime()));
            row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
        }
    }
}
