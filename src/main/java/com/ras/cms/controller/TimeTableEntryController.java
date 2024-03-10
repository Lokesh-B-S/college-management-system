package com.ras.cms.controller;

//import com.ras.cms.dao.TimeSlotRepository;
import com.ras.cms.domain.*;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.departmentsemsec.DepartmentSemSecService;
import com.ras.cms.service.course.CourseService;
import com.ras.cms.service.coursefaculty.CourseFacultyService;
import com.ras.cms.service.day.DayService;
import com.ras.cms.service.section.SectionService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.timeTableEntry.TimeTableEntryService;
//import com.ras.cms.service.timeslot.TimeSlotService;
import com.ras.cms.service.timeslotselection.TimeSlotSelectionService;
        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

        import java.util.List;

@Controller
//@RequestMapping("/hod")
public class TimeTableEntryController {
    @Autowired
    private TimeTableEntryService timeTableEntryService;
    @Autowired
    private DayService dayService;
//    @Autowired
//    private TimeSlotService timeSlotService;
    @Autowired
    private CourseService courseService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private SectionService sectionService;
    @Autowired
    private DepartmentSemSecService departmentSemSecService;

    @Autowired
    private TimeSlotSelectionService timeSlotSelectionService;

    @Autowired
    private CourseFacultyService courseFacultyService;


    @GetMapping({"/admin/listTT/{id}","/hod/listTT/{id}"})
    public String viewTimeTable(Model model,  @PathVariable(required = false, name = "id") Long id){
        List<TimeTableEntry> entries = timeTableEntryService.getEntriesByDepartmentSemSecId(id);
        model.addAttribute("entries", entries);

        model.addAttribute("id",id);

        List<Day> days = dayService.findAll();
        model.addAttribute("days",days);

        //timeSlots as per the client added
        DepartmentSemSec departmentSemSec = departmentSemSecService.findOne(id);
        if(departmentSemSec!=null) {
            List<TimeSlotSelection> timeSlots = timeSlotSelectionService.getTimeSlotSelectionsByDepartment(departmentSemSec.getDepartment());
            model.addAttribute("timeSlots", timeSlots);
            List<Course> courses = courseService.getCoursesByDepartmentAndSemester(departmentSemSec.getDepartment(),departmentSemSec.getSemester());
            model.addAttribute("courses", courses);
            List<CourseFaculty> allotments = courseFacultyService.getAllotmentsByDepartmentAndSemesterAndSection(departmentSemSec.getDepartment(),departmentSemSec.getSemester(),departmentSemSec.getSection());
            model.addAttribute("allotments",allotments);

            model.addAttribute("departmentSemSec",departmentSemSec);

        }
        else{
            List<TimeSlotSelection> timeSlots = timeSlotSelectionService.findAll();
            model.addAttribute("timeSlots", timeSlots);
            model.addAttribute("courses", getCourses());
        }

        return "/timeTableList";
    }


    @GetMapping({"/admin/selectTimeTable","/hod/selectTimeTable"})
    public String selectTimeTable(Model model){

    List<Semester> semesters = semesterService.findAll();
    List<Section> sections = sectionService.findAll();

    model.addAttribute("departments", getDepartments());
    model.addAttribute("semesters", semesters);
    model.addAttribute("sections",sections);

    model.addAttribute("departmentSemSec", new DepartmentSemSec());

    return "/timeTableSelection";
    }

    @PostMapping({"/admin/selectTimeTable","/hod/selectTimeTable"})
    public String selectandsubmitTimeTable(Model model, DepartmentSemSec departmentSemSec, RedirectAttributes redirectAttributes){

        if (departmentSemSecService.existsEntry(departmentSemSec)) {
            redirectAttributes.addFlashAttribute("message", "Entry already exists.");
            DepartmentSemSec departmentSemSec1 = departmentSemSecService.findRow(departmentSemSec);
            return "redirect:/hod/editTimeTable?id=" + departmentSemSec1.getDepartmentSemSecId();
        }
        else {
            try {
                departmentSemSecService.saveEntry(departmentSemSec);
                try{
                    DepartmentSemSec departmentSemSec1 = departmentSemSecService.findOne(departmentSemSec.getDepartmentSemSecId());
                    model.addAttribute("departmentSemSec1",departmentSemSec1);
                    return "redirect:/hod/editTimeTable?id=" + departmentSemSec.getDepartmentSemSecId();
                }catch (Exception e){}
            } catch (Exception e) {
            }
        }
        return "/403";
    }

    @GetMapping({"admin/editTimeTable","admin/editTimeTable/{id}","hod/editTimeTable","hod/editTimeTable/{id}"})
    public String showAddForm(Model model, @RequestParam(name = "id", required = false) Long id)
    {
       if (null != id) {
           model.addAttribute("departmentSemSec1",departmentSemSecService.findOne(id));
       }
       else{
           model.addAttribute("departmentSemSec1",new DepartmentSemSec());
       }

        //get all days
        List<Day> days = dayService.findAll();
        model.addAttribute("days", days);

                       //to get department from departmentsemsec table and match with timeslotselection table to retrieve only those timeslots instead of all timeslots
                        DepartmentSemSec departmentSemSec = departmentSemSecService.findOne(id);
                        if(departmentSemSec!=null) {
                            List<TimeSlotSelection> timeSlots = timeSlotSelectionService.getTimeSlotSelectionsByDepartment(departmentSemSec.getDepartment());
                            model.addAttribute("timeSlots", timeSlots);
                            List<Course> courses = courseService.getCoursesByDepartmentAndSemester(departmentSemSec.getDepartment(),departmentSemSec.getSemester());
                            model.addAttribute("courses", courses);

                            model.addAttribute("departmentSemSec", departmentSemSec);

                        }
                        //i dont think this else part is needed - 20/2/2024
                        else{
                            List<TimeSlotSelection> timeSlots = timeSlotSelectionService.findAll();
                            model.addAttribute("timeSlots", timeSlots);
                           model.addAttribute("courses", getCourses());
                            model.addAttribute("departmentSemSec", departmentSemSec);

                        }
        model.addAttribute("timeTableEntry", new TimeTableEntry()); // This is needed for the form

        return "/timeTableEdit";
    }

    @PostMapping({"/admin/editTimeTable","/hod/editTimeTable"})
    public String addEntry(Model model, TimeTableEntry timeTableEntry,
                           DepartmentSemSec departmentSemSec1,
                           RedirectAttributes attributes,
                           @RequestParam Long departmentSemSecId){
        try {
            // Check if an entry already exists for the given parameters
            boolean entryExists = timeTableEntryService.doesEntryExist(departmentSemSecId, timeTableEntry.getDay(), timeTableEntry.getTimeSlot());  //no need of mentioning timeTableEntry.getCourse too coz it results adding multiple courses

            if(!entryExists) {
                timeTableEntry.setDepartmentSemSecId(departmentSemSec1.getDepartmentSemSecId());
                timeTableEntry.setDepartment(departmentSemSec1.getDepartment());
                timeTableEntry.setSemester(departmentSemSec1.getSemester());
                timeTableEntry.setSection(departmentSemSec1.getSection());

                timeTableEntryService.saveEntry(timeTableEntry);
                System.out.println("Success");
                // model.addAttribute("alertType", "success");
                attributes.addFlashAttribute("successMessage", "Time table saved successfully");
            }
            else{
                attributes.addFlashAttribute("EntryAlreadyExistsError", "Sorry! already there is an entry exists");
            }

            } catch (Exception e) {
            System.out.println("fail");
            System.out.println(e);
           // model.addAttribute("alertType", "danger");
            attributes.addFlashAttribute("errorMessage", "Time table couldn't be saved!");

        }
        //return "/timeTableEdit";
        return "redirect:/hod/editTimeTable?id=" + departmentSemSecId;
    }

    @GetMapping({"/admin/deleteTimeTable/{departmentSemSecId}","/hod/deleteTimeTable/{departmentSemSecId}"})
    public String deleteTimeTableEntry(Model model, TimeTableEntry timeTableEntry, DepartmentSemSec departmentSemSec1, RedirectAttributes attributes,  @PathVariable(name = "departmentSemSecId") Long departmentSemSecId,
                                       @RequestParam(name = "day") Day day,
                                       @RequestParam(name = "timeSlot") TimeSlotSelection timeSlot,
                                       @RequestParam(name = "course") Course course) {
        System.out.println(timeTableEntry.getTimeSlot());
        System.out.println(departmentSemSecId);
        System.out.println(day);
        System.out.println(timeSlot);
        System.out.println(course);

        try {
            // Check if an entry already exists for the given parameters
            //boolean entryExistsToDelete = timeTableEntryService.doesEntryExist(departmentSemSecId, day, timeSlot, course);
            boolean entryExistsToDelete = timeTableEntryService.doesEntryExist(departmentSemSecId, day, timeSlot);

            if(entryExistsToDelete) {
                TimeTableEntry entryToDelete = timeTableEntryService.getEntry(departmentSemSecId, day, timeSlot, course);
                timeTableEntryService.deleteTimeTableEntry(entryToDelete.getId());
                System.out.println("Deleted");
                attributes.addFlashAttribute("DeleteSuccessMessage", "entry deleted successfully.....");
            }
            else{
                attributes.addFlashAttribute("EntryDoesNotExistError", "Sorry! this entry doesn't exist to delete, add it first.....");
            }
        } catch (Exception e) {
            System.out.println(e);

        }
       // return "redirect:/admin/selectTimeTable";
        return "redirect:/hod/editTimeTable?id=" + departmentSemSecId;


    }



    private Object getCourses() {
        return  courseService.findAll();
    }
    private Object getDepartments() {return departmentService.findAll(); }

}
