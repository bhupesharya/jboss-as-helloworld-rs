/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the 
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.rshelloworld;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

/**
 * A simple REST service which is able to say hello to someone using
 * HelloService
 * Please take a look at the web.xml where JAX-RS is enabled
 * 
 * @author gbrey@redhat.com
 * 
 */

@Path("/")
public class HelloWorld {
	@Inject
	HelloService helloService;

	@GET
	@Path("/json")
	@Produces({ "application/json" })
	public String getHelloWorldJSON() {
		return "{\"result\":\"" + helloService.createHelloMessage("World") + "\"}";
	}

	@GET
	@Path("/xml")
	@Produces({ "application/xml" })
	public String getHelloWorldXML() {
		return "<xml><result>" + helloService.createHelloMessage("World")
				+ "</result></xml>";
	}
	
	 @GET
	    @Path("/postxml")
	   @Produces(MediaType.TEXT_PLAIN)   
	 // @Consumes("text/plain")
	    public Response getHelloWorldPostxml(@QueryParam("inputcode") String inputcode) throws Exception {
	    	System.out.println("inputcode "+inputcode);
	    String	Contentforreturn="";
	    	long processid = 0;
	    	if (new String("*101").equals(inputcode))
	    	{
	    		System.out.println("inside codition "+inputcode);
	    		processid = CreateProcesses.startProcess();
	    		FileOutputStream fout = new FileOutputStream("/tmp/myfile.txt");
	    		new PrintStream(fout).println(processid);
	    		 fout.close();
	    		//Contentforreturn = processid;
	    		//String taaskid =   TaskID.id(processid); 
	    		 String taskContents = CreateProcesses.getTaskContents(processid);
	    		//Contentforreturn = staticmenu.getMenu(taaskid);
	    		 Contentforreturn = taskContents;
	    		
	    	}
	    	else 
	    	{
	    		FileInputStream	fin = new FileInputStream ("/tmp/myfile.txt");
	    		//processid = new DataInputStream(fin).readLine();
	    			
	    		System.out.println("process is in else" + processid);
	    		fin.close();
	            //String tasskid = TaskID.id(processid);
	            //String taskresult = startprocess.sp(tasskid);
	            //System.out.println("result of taskresult" + taskresult);
	            //String Resultofcompleteprocesss = completeprocess.CP(tasskid,inputcode);
	            //System.out.println("result of complete process" + Resultofcompleteprocesss)
	            
	            //tasskid = TaskID.id(processid);
	            //Contentforreturn = getcontent.GT(tasskid);
	            Contentforreturn = CreateProcesses.completeTask(processid, inputcode);
	            
	    	}
	    	 return	Response.ok(Contentforreturn).build();
	    	
	    }
	    
	

	    
	@POST
	@Path("/msisdnapi")

	@Produces(MediaType.TEXT_PLAIN)   
	// @Consumes("text/plain")
	public String getHelloWorldmsisdnapi(@QueryParam("id") String id) throws Exception {
	 //public Response getHelloWorldPost(String id) throws Exception {
		System.out.println(id);
	// return Response.ok("email=" + id).build();
		return "<COMMAND> <TYPE>CCOREQ</TYPE> <MSISDN>7700000125</MSISDN>   <AGNTCODE>00017</AGNTCODE> <AMOUNT>2</AMOUNT> <PROVIDER>101</PROVIDER> <PAYID>12</PAYID><PROVIDER2>101</PROVIDER2> <PAYID2>12</PAYID2> <MPIN>1357</MPIN> <PIN>1357</PIN> <LANGUAGE1>1</LANGUAGE1> <LANGUAGE2>1</LANGUAGE2> </COMMAND>";
	// String	processid = restclient.startProcess();
	//return Response.ok(TaskID.id("405")).build(); 
	   
	}

	@POST
	@Path("/pinapi")

	@Produces(MediaType.TEXT_PLAIN)   
	// @Consumes("text/plain")
	//public String getHelloWorldpinapi(@QueryParam("id") String id) throws Exception {
	public String getHelloWorldpinapi(String id) throws Exception {
	 //public Response getHelloWorldPost(String id) throws Exception {

		System.out.println(id);
	// return Response.ok("email=" + id).build();
		return "<COMMAND><pincheck>test<pincheck> <TYPE>CCOREQ</TYPE> <MSISDN>7700000125</MSISDN>   <AGNTCODE>00017</AGNTCODE> <AMOUNT>2</AMOUNT> <PROVIDER>101</PROVIDER> <PAYID>12</PAYID><PROVIDER2>101</PROVIDER2> <PAYID2>12</PAYID2> <MPIN>1357</MPIN> <PIN>1357</PIN> <LANGUAGE1>1</LANGUAGE1> <LANGUAGE2>1</LANGUAGE2> </COMMAND>";
	// String	processid = restclient.startProcess();
	//return Response.ok(TaskID.id("405")).build(); 
	   
	}


	@POST
	@Path("/moneyapi")

	@Produces(MediaType.TEXT_PLAIN)   
	// @Consumes("text/plain")
	public String getHelloWorldmoneyapi(@QueryParam("id") String id) throws Exception {
	 //public Response getHelloWorldPost(String id) throws Exception {
		System.out.println(id);
	// return Response.ok("email=" + id).build();
		return "<COMMAND> <TYPE>CBERESP</TYPE> <TXNID>ES141001.1903.E00001</TXNID> <TXNSTATUS>200</TXNSTATUS> <BALANCE>9999515.78</BALANCE> <MESSAGE>you have currently 9999446.78INR with fic balance 0.00 available on your account and Your Normal wallet of MFS provider MFS1 is debited with service charge 1.00 INR, Commission 0.00 INR and TAX 1.00 INR with transaction ID:ES141001.1903.E00001.</MESSAGE> <FRBALANCE>-69.00</FRBALANCE> <FICBALANCE>0.00</FICBALANCE> <OTHERWALLETS> </OTHERWALLETS> <TRID>7700000125201410011903E0412</TRID> <IVR-RESPONSE>1107#9999446.78#</IVR-RESPONSE> </COMMAND>";
	// String	processid = restclient.startProcess();
	//return Response.ok(TaskID.id("405")).build(); 
	   
	}


	@POST
	@Path("/getpackapi")

	@Produces(MediaType.TEXT_PLAIN)   
	// @Consumes("text/plain")
	public String getHelloWorldgetpackapi(@QueryParam("id") String id) throws Exception {
	 //public Response getHelloWorldPost(String id) throws Exception {
		System.out.println(id);
	// return Response.ok("email=" + id).build();
		return "<COMMAND><TYPE>GETPACKRESP</TYPE><PACK><PACKID>CH3</PACKID><DESC>Children pack</DESC><VALIDITY>3 month</VALIDITY><AMOUNT>95</AMOUNT></PACK><PACK><PACKID>CH6</ PACKID><DESC>Children pack</DESC><VALIDITY>6 month</VALIDITY><AMOUNT>195</AMOUNT></PACK><TXNSTATUS>200</TXNSTATUS><MESSAGE>Success</MESSAGE></COMMAND>";
	// String	processid = restclient.startProcess();
	//return Response.ok(TaskID.id("405")).build(); 
	   
	}


	@POST
	@Path("/merchantapi")

	@Produces(MediaType.TEXT_PLAIN)   
	// @Consumes("text/plain")
	public String getHelloWorldmerchantapi(@QueryParam("id") String id) throws Exception {
	 //public Response getHelloWorldPost(String id) throws Exception {
		System.out.println(id);
	// return Response.ok("email=" + id).build();
		return "<COMMAND> <TYPE>RMPRREQ</TYPE> <MSISDN>7700000125</MSISDN> <PROVIDER>101</PROVIDER> <MPIN>1357</MPIN> <PIN>1357</PIN> <TXNID>MP140603.1208.C00007</TXNID> <STATUS>0</STATUS> <LANGUAGE1>1</LANGUAGE1> </COMMAND>";
	// String	processid = restclient.startProcess();
	//return Response.ok(TaskID.id("405")).build(); 
	   
	}

	@POST
	@Path("/subscriberapi")

	@Produces(MediaType.TEXT_PLAIN)   
	// @Consumes("text/plain")
	public String getHelloWorldsubscriberapi(@QueryParam("id") String id) throws Exception {
	 //public Response getHelloWorldPost(String id) throws Exception {
		System.out.println(id);
	// return Response.ok("email=" + id).build();
		return "<COMMAND> <TYPE> SUBPACKRESP</TYPE> <PACKID>CH3</PACKID> <TXNID>CO.233.32432.324</TXNID> <MERCHANTTXNID>CO141001.1831.E00019</MERCHANTTXNID> <TXNSTATUS>200</TXNSTATUS> <MESSAGE>Pack subscribed.</MESSAGE> </COMMAND>";
	// String	processid = restclient.startProcess();
	//return Response.ok(TaskID.id("405")).build(); 
	   
	}


}
