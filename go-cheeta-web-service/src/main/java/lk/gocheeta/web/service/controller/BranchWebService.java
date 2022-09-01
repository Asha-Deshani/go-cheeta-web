/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package lk.gocheeta.web.service.controller;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import lk.gocheeta.web.service.service.BranchService;

/**
 *
 * @author asha
 */
@WebService(serviceName = "BranchWebService")
public class BranchWebService {
    
    private BranchService branchService;

    public BranchWebService() {
        branchService = new BranchService();
    }
    
   

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello kamal" + txt + " !";
    }
    
    //operationName = "getbranches"
    
    public List<Branch> getBranches(){
        branchService.getBranch(0);
    }
    )
}
