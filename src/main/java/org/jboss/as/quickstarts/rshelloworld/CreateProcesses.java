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
//import org.kie.internal.task.api.InternalTaskService;
import java.util.List;
//import org.kie.api.*;
//import org.jbpm.task.utils.ContentMarshallerContext;
//import org.jbpm.task.utils.ContentMarshallerHelper;

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
		String taskContents = getTaskContents();
		String taskName = completeTask(output_);

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
	public static String completeTask(String output_){
		   
		   List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("bpmsadmin", "en-UK");

        TaskSummary task = list.get(0);
        long taskId = task.getId();

        System.out.println("Completing Task" + task.getName());

        taskService.start(taskId, "bpmsadmin");
         Map<String, Object> params = new HashMap<String, Object>();
        params.put("output_", output_);

        taskService.complete(taskId, "bpmsadmin", params);
        System.out.println("Task Completed" + taskId);
        return task.getName();
	
	}
	
	public static String getTaskContents(){
		   
		   List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("bpmsadmin", "en-UK");

        TaskSummary task = list.get(0);
        long taskId = task.getId();

        System.out.println("Showing Task Contents" + task.getName());
        return task.getName();

        
        /* Map<String, Object> params = getTaskContent(taskId);
    
        System.out.println("Task Contents are" + params.get(0)); */
	
	}
	
	/* public static Map<String, Object> getTaskContent( long taskId) {
			  Task taskById = taskService.getTaskById(taskId);
              Content contentById = taskService.getContentById(taskById.getTaskData().getDocumentContentId());
              ContentMarshallerContext context = getMarshallerContext(taskById);
              Object unmarshalledObject = ContentMarshallerHelper.unmarshall(contentById.getContent(), context.getEnvironment(), context.getClassloader());
              if (!(unmarshalledObject instanceof Map)) {
                  throw new IllegalStateException(" The Task Content Needs to be a Map in order to use this method and it was: "+unmarshalledObject.getClass());
      
              }
              Map<String, Object> content = (Map<String, Object>) unmarshalledObject;
              return content;
	} */
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

