import static org.junit.Assert.*;

import org.junit.Test;

public class Tests { 

	
	@Test
	public void givenTest(){
		Schedule schedule = new Schedule();
		schedule.insert(8); //adds job 0 with time 8
		Schedule.Job j1 = schedule.insert(3); //adds job 1 with time 3
		schedule.insert(5); //adds job 2 with time 5
		assertEquals(8,schedule.finish()); //should return 8, since job 0 takes time 8 to complete.
		

		schedule.get(0).requires(schedule.get(2)); //job 2 must precede job 0
		System.out.println(schedule.finish());
		
		assertEquals(13,schedule.finish()); //should return 13 (job 0 cannot start until time 5)
		
		
		
		schedule.get(0).requires(j1); //job 1 must precede job 0
		assertEquals(13,schedule.finish()); //should return 13
		System.out.println(schedule.finish());
		
		assertEquals(5,schedule.get(0).start()); //should return 5
		j1.start(); //should return 0
		assertEquals(0,schedule.get(2).start()); //should return 0
		j1.requires(schedule.get(2)); //job 2 must precede job 1
		
		System.out.println(schedule.finish());
		
		assertEquals(16,schedule.finish()); //should return 16
		
		
		assertEquals(8,schedule.get(0).start()); //should return 8
		assertEquals(5,schedule.get(1).start()); //should return 5
		assertEquals(0,schedule.get(2).start()); //should return 0
		schedule.get(1).requires(schedule.get(0)); //job 0 must precede job 1 (creates loop)
		assertEquals(-1,schedule.finish()); //should return -1
		assertEquals(-1,schedule.get(0).start()); //should return -1
		assertEquals(-1,schedule.get(1).start()); //should return -1
		
		System.out.println(schedule.get(2).start());
		assertEquals(0,schedule.get(2).start()); //should return 0 (no loops in prerequisites)
		
		
		
		

	}
	@Test
	public void test(){
		Schedule schedule2 = new Schedule();
		schedule2.insert(8); //adds job 0 with time 8
		Schedule.Job j1 = schedule2.insert(3); //adds job 1 with time 3
		schedule2.insert(5); //adds job 2 with time 5
		assertEquals(8,schedule2.finish()); //should return 8, since job 0 takes time 8 to complete.
		

		schedule2.get(0).requires(schedule2.get(2)); //job 2 must precede job 0
		System.out.println(schedule2.finish());
		
		assertEquals(13,schedule2.finish()); //should return 13 (job 0 cannot start until time 5)
		
		
		
		schedule2.get(0).requires(j1); //job 1 must precede job 0
		assertEquals(13,schedule2.finish()); //should return 13
		System.out.println(schedule2.finish());
		
		assertEquals(5,schedule2.get(0).start()); //should return 5
		j1.start(); //should return 0
		assertEquals(0,schedule2.get(2).start()); //should return 0
		j1.requires(schedule2.get(2)); //job 2 must precede job 1
		
		System.out.println(schedule2.finish());
		
		assertEquals(16,schedule2.finish()); //should return 16
		
		
		assertEquals(8,schedule2.get(0).start()); //should return 8
		assertEquals(5,schedule2.get(1).start()); //should return 5
		assertEquals(0,schedule2.get(2).start()); //should return 0
		
		schedule2.insert(10); // j3 added w t= 10
		schedule2.insert(20); //j4 added w t =5 
		
		
		System.out.println(schedule2.finish());
		
		schedule2.get(2).requires(schedule2.get(3)); //job 2 must precede job 0
		
		System.out.println(schedule2.finish()); //should be 26
		schedule2.get(0).requires(schedule2.get(4)); //job 2 must precede job 0

		System.out.println(schedule2.finish()); //should be 28
		System.out.println( "break" );
		
		
		
	}
	@Test
	public void biggerTest() {
	    Schedule schedule = new Schedule();

	    
	    schedule.insert(10);  							//insert 10 jobs with various start times
	    Schedule.Job j1 = schedule.insert(5);  
	    Schedule.Job j2 = schedule.insert(8);  
	    Schedule.Job j3 = schedule.insert(12); 
	    Schedule.Job j4 = schedule.insert(6);  
	    Schedule.Job j5 = schedule.insert(7);  
	    Schedule.Job j6 = schedule.insert(4);  
	    Schedule.Job j7 = schedule.insert(9);  
	    Schedule.Job j8 = schedule.insert(3);  
	    Schedule.Job j9 = schedule.insert(15); 
	    assertEquals(15, schedule.finish()); 
	    
	    												//create dependencies
	    
	    schedule.get(0).requires(schedule.get(1)); 
	    schedule.get(0).requires(schedule.get(2)); 
	    schedule.get(3).requires(schedule.get(2)); 
	    schedule.get(4).requires(schedule.get(3)); 
	    schedule.get(5).requires(schedule.get(4)); 
	    schedule.get(6).requires(schedule.get(5)); 
	    schedule.get(7).requires(schedule.get(1)); 
	    schedule.get(7).requires(schedule.get(3)); 
	    schedule.get(9).requires(schedule.get(8)); 
	    assertEquals(37, schedule.finish());
	    												//check job specific start times
	   
	    assertEquals(8, schedule.get(0).start());  
	    assertEquals(0, schedule.get(1).start());  
	    assertEquals(0, schedule.get(2).start());  
	    assertEquals(8, schedule.get(3).start());  
	    assertEquals(20, schedule.get(4).start()); 
	    assertEquals(26, schedule.get(5).start()); 
	    assertEquals(33, schedule.get(6).start()); 
	    
	
	}

	@Test
	public void allDependOnOne() {
	    Schedule schedule = new Schedule();
	    												//create 4 jobs all 1-3 require j0 directly
	    Schedule.Job j0 = schedule.insert(6); 
	    Schedule.Job j1 = schedule.insert(3);
	    Schedule.Job j2 = schedule.insert(4);
	    Schedule.Job j3 = schedule.insert(5);
	    j1.requires(j0);
	    j2.requires(j0);
	    j3.requires(j0);

	    
	    assertEquals(11, schedule.finish());			// it takes time 6 for the preq  to finish then the longest time is 5 so 5+6
	    assertEquals(0, j0.start());  
	    assertEquals(6, j1.start());
	    assertEquals(6, j2.start());
	    assertEquals(6, j3.start());
	}


}
