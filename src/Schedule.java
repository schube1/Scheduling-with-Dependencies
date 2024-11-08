 import java.util.*;


public class Schedule {
	
    private List <Job> jobs;                 // all of the jobs
    private Map <Job, List <Job>> prereqs;     // adj list of prereqs
    private Map <Job, List <Job>> dependents; // ajd list of dependents

    
    public Schedule() {
    	
        jobs = new ArrayList<>();
        prereqs = new HashMap<>();
        dependents = new HashMap<>();
    }
 
    
    public Job insert(int time) {
    	
        Job j = new Job(time, jobs.size());
        jobs.add(j);
        prereqs.put(j, new ArrayList<>()); 			// maps j to an new prereq list
        dependents.put(j, new ArrayList<>());		// maps j to an new dependent list
        return j;
    }

    
    public Job get(int index) {
        return jobs.get(index);			// simply returns the index 
    }

    
    public int finish() {
        
        if (loop()) {
        	return -1;			// if there is a loop we dont know its finish hence return -1
        }

        int max = 0;=
        for (Job job : jobs) { 
        	
            int startJob = job.start();
            if (startJob == -1) {
            	return -1; 				// loop found 
            }
            int completeJob = startJob + job.time;		// start time + time to finish = the total
            max = Math.max(max, completeJob);			// makes sure max is the longest completion time weve seen
        
        }
        return max;
    }

    
    private boolean loop() {
    	
        Map<Job, Integer> inDegree = new HashMap<>();	// need inDegrees for kahns to idetify which is 0

        for (Job job : jobs) {
            inDegree.put(job, 0);						// add all jobs inDegrees =0
        }
        
        for (Job job : prereqs.keySet()) {
        	
            for (Job prereq : prereqs.get(job)) {
            	
                inDegree.put(job, inDegree.get(job) + 1);	// go thru all preqs of the curr job and increment the corresponing indegree
            }
        }
        
        Queue<Job> order = new LinkedList<>(); 
        
        for (Job job : inDegree.keySet()) {
            if (inDegree.get(job) == 0) {
            	order.add(job);		// adds all jobs with no preqs to the queue as those are topoligcally first
            }
        }
        int count = 0;										// need to track how many jobs have been processed
        while (!order.isEmpty()) {
        	
            Job job = order.remove();						
            count++;
            
            for (Job dep : dependents.get(job)) { 
            	
                inDegree.put(dep, inDegree.get(dep) - 1);  // decremtn the inDeg of all dependents by 1  if now 0 add to the que
                if (inDegree.get(dep) == 0) {
                	order.add(dep);						
                }
            }
        }
        return count != jobs.size();						// count of all processed != the job size we know there has been a loop(s)
    }
 
   
    public class Job {
        
        private int index;               
        private int time;                
        private Integer startBefore; // will b null if there is not a earlier starting time  

        
        private Job(int time, int index) {
        	
            this.index = index; 
            this.time = time;
            this.startBefore = null;
        }

       
        public void requires(Job j) { // add to both adjancey lists and recalculate the earliest starting time
        	
        	prereqs.get(this).add(j);          
        	dependents.get(j).add(this);   
        	
        	recalculateStart();          
        }

       
        private void recalculateStart() {
        	
            if (startBefore != null) {
            	startBefore = null;  //need to reset
               
                for (Job dep : dependents.get(this)) {	
                	dep.recalculateStart();    // recursively reset all of the tthe jobs dependants starttimes
                }
            } 
        }

       
        public int start() {
        	
            if (startBefore != null) return startBefore;
            
            Set<Job> stack = new HashSet<>();    // hashset allows for easy duplicate detection
            int result = prereqStart(stack);
            
            if (result == -1) {
            	startBefore = null;
            } else {
            	startBefore = result;		 // pre-known start return this
            }
           
            return result; 
        }

        
        
        
        private int prereqStart(Set<Job> stack) {
        	
            if (stack.contains(this)) {   // already exists 
            	return -1; 
            }
            
            if (startBefore != null) {
            	return startBefore;
             }

            stack.add(this);

            int max = 0;
            
            for (Job prereq : prereqs.get(this)) {
            		
                int prereqStartTime = prereq.prereqStart(stack);
                if (prereqStartTime == -1) {
                	stack.remove(this);
                    return -1; 
                }
                int prereqFinishTime = prereqStartTime + prereq.time;
                
                max = Math.max(max, prereqFinishTime);
            }

            stack.remove(this);
            
            startBefore = max;
            return startBefore;
        }
    }

}