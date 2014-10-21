package org.jboss.as.quickstarts.rshelloworld;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.services.client.api.RemoteRestRuntimeFactory;
import org.kie.api.task.model.TaskSummary;
import org.kie.api.task.TaskService; 
import org.kie.api.task.model.Task;
import org.kie.api.task.model.Content;
import java.util.List;


public class CreateProcesses
{
	static long processInstanceId;
	static TaskService taskService;
	//static InternalTaskService internalTaskService;
	
	public static void main(String[] args)
	{
		if( args.length < 2 || args.length > 4 )
		{
			System.out
					.println( "Usage: java -jar jboss-mortgage-demo-client.jar username password [http://localhost:8080/business-central [com.redhat.bpms.examples:mortgage:1]]" );
			return;
		}

		String userId = args[0];
		String password = args[1];

		String applicationContext;
		if( args.length > 2 )
		{
			applicationContext = args[2];
		}
		else
		{
			applicationContext = "http://localhost:8080/business-central";
		}

		String deploymentId;
		if( args.length > 3 )
		{
			deploymentId = args[3];
		}
		else
		{
			deploymentId = "org.jbpm:comvivaPOC:1.0";
		}

		long processInstanceId = startProcess();
		String output_ = new String("1234");
		String taskContents = getTaskContents(processInstanceId);
		String taskName = completeTask(processInstanceId, output_);

		System.out.println("Successfully loaded processes into your JBoss BPM Suite Server. Check the server log to see the application log outputs.");
	}

	public static long startProcess()
	{
		RuntimeEngine runtimeEngine = getRuntimeEngine();
		KieSession kieSession = runtimeEngine.getKieSession();
		taskService = runtimeEngine.getTaskService();
		//internalTaskService = (InternalTaskService)runtimeEngine.getTaskService();
		long processInstanceId = kieSession.startProcess( "comvivaPOC.comvivaUC2").getId();
		System.out.println("Process Instance id is " + processInstanceId);
		return processInstanceId;
		
		
	}
	public static String completeTask(long processInstanceId, String output_){
		   
		   List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("bpmsadmin", "en-UK");

        TaskSummary task = list.get(0);
        long taskId = task.getId();

        System.out.println("Completing Task" + task.getName());

        taskService.start(taskId, "bpmsadmin");
         Map<String, Object> params = new HashMap<String, Object>();
        params.put("output_", output_);

        taskService.complete(taskId, "bpmsadmin", params);
        System.out.println("Task Completed" + taskId);
        return getTaskContents(processInstanceId);
	
	}
	
	public static String getTaskContents(long processInstanceId){
		   
		   List<Long> list = taskService.getTasksByProcessInstanceId(processInstanceId);

        Task task = taskService.getTaskById(list.get(0).longValue());
        long taskId = task.getId();

        System.out.println("Showing Task Contents for id" + taskId + "" + task.getNames());
        Map<String, Object> taskContents = new HashMap<String, Object>();
        taskContents = taskService.getTaskContent(taskId);
        //if (taskContents != null) {
        System.out.println("Task Content size is " + taskContents.size());
        System.out.println("Task String is" + taskContents.toString());
        return taskContents.toString();
        //}
        //else {
        	//return "Empty Task Contents";
        //}
        

        
        /* Map<String, Object> params = getTaskContent(taskId);
    
        System.out.println("Task Contents are" + params.get(0)); */
	
	}
	
	private static RuntimeEngine getRuntimeEngine()
	{
		try
		{
			String applicationContext = "http://localhost:8080/business-central";
			String deploymentId = "org.jbpm:comvivaPOC:1.0";
			String userId = "bpmsadmin";
			String password = "admin@123";
			URL jbpmURL = new URL( applicationContext );
			RemoteRestRuntimeFactory remoteRestSessionFactory = new RemoteRestRuntimeFactory( deploymentId, jbpmURL, userId, password );
			RuntimeEngine runtimeEngine = remoteRestSessionFactory.newRuntimeEngine();
			return runtimeEngine;
		}
		catch( MalformedURLException e )
		{
			throw new IllegalStateException( "This URL is always expected to be valid!", e );
		}
	}

}

