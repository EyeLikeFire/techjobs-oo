package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam("id") int id) {


        Job job = jobData.findById(id);
        model.addAttribute(job);
        // TODO #1 - get the Job with the given ID and pass it into the view // C O M P L E T E //

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            model.addAttribute("JobForm", jobForm);
            return "new-job";
        }
        // this variable will be the job
        // This line of code will use the jobData.add function to add job to the database



        String name = jobForm.getName();
        // "employer" below creates a new "Employer" obj.
        // Next, using the employer ID (jobFrom.getEmployerID)...
        // ...it searches for IDs in jobData.getEmployers.
        Employer employer = jobData.getEmployers().findById( jobForm.getEmployerId() );
        Location location = jobData.getLocations().findById( jobForm.getLocationId() );
        PositionType positionType = jobData.getPositionTypes().findById( jobForm.getPositionTypeId() );
        CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById( jobForm.getCoreCompetencyId() );



        Job newJob = new Job(
                name, employer, location, positionType, coreCompetency
        );
        jobData.add(newJob);




        //THIS works:
        System.out.println("G G E T  C C O O L  S S T U F F");
        System.out.println(jobForm.getCoreCompetencyId());
        System.out.println(jobForm.getEmployerId());
        System.out.println(jobForm.getName());


        return ("redirect:?id=" + "1");

    }
}
