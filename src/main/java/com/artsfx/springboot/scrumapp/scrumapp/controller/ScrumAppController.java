package com.artsfx.springboot.scrumapp.scrumapp.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.artsfx.springboot.scrumapp.scrumapp.dto.UserDto;
import com.artsfx.springboot.scrumapp.scrumapp.entity.Project;
import com.artsfx.springboot.scrumapp.scrumapp.entity.Task;
import com.artsfx.springboot.scrumapp.scrumapp.entity.Team;
import com.artsfx.springboot.scrumapp.scrumapp.entity.User;
import com.artsfx.springboot.scrumapp.scrumapp.exceptions.EmailExistsException;
import com.artsfx.springboot.scrumapp.scrumapp.repository.ProjectRepository;
import com.artsfx.springboot.scrumapp.scrumapp.repository.TaskRepository;
import com.artsfx.springboot.scrumapp.scrumapp.repository.TeamRepository;
import com.artsfx.springboot.scrumapp.scrumapp.repository.UserRepository;
import com.artsfx.springboot.scrumapp.scrumapp.services.IUserService;

@Controller
public class ScrumAppController {

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	IUserService userService;
	
	
	@GetMapping("/home")
	public String getHome(Model theModel) {

		//Team theTeam = teamRepository.getOne(teamId);

		//Send new team object to the view 
		theModel.addAttribute("team", new Team());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Optional<User> optionalUser = userRepository.findByUserName(currentPrincipalName);

			theModel.addAttribute("username", currentPrincipalName);
			theModel.addAttribute("project", new Project());
			theModel.addAttribute("task", new Task());
			
		optionalUser.ifPresent(user -> {
			List<Team> teams = user.getTeams();
			theModel.addAttribute("teams", teams);
			
//			
//			teams.forEach(team -> {
//				System.out.println("FGGFGFD");
//				//theModel.addAttribute("projects", team.getProjects());
//			});
		});

		return "home";
	}

	@GetMapping("showFormNewTeam")
	public String showFormNewTeam(Model theModel) {
		
		theModel.addAttribute("team", new Team());
		
		return "form-new-team";
	}
	
	@GetMapping("showFormNewProject")
	public String showFormNewProject(Model theModel, @RequestParam("teamId") int teamId) {
		
		System.out.println("Team id is :" + teamId);
		theModel.addAttribute("project", new Project());
		
		return "form-new-project";
	}
	
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	
	@GetMapping("/registrationForm")
	public String showRegistrationForm(UserDto userDto, Model theModel) {
	
		theModel.addAttribute("user", userDto);
		
			return "form-registration";
	
	}
	
	@GetMapping("/admin/home")
	public String getAdminHome() {
		return "adminhome";
	}


	
	@GetMapping("/accessDenied")
	public String getErrorPage() {
		return "403";
	}
	
	@PostMapping("/saveTeam")
	public String saveTeam(@Valid @ModelAttribute("team") Team theTeam, BindingResult bindingResult) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (bindingResult.hasErrors()) {
			System.out.println("=======================>"+bindingResult);

			return "form-new-team";
		}
		
		Optional<User> optionalUser = userRepository.findByUserName(currentPrincipalName);
		optionalUser.ifPresent(user -> {

		user.addTeam(theTeam);
		
		userRepository.saveAndFlush(user);

		});	
		
		return "redirect:/home";

		
	}

	@PostMapping("/saveProject")
	public String saveProject(@Valid @ModelAttribute("project") Project theProject, BindingResult bindingResult,@RequestParam("teamId") int teamId) {

		if (bindingResult.hasErrors()) {
			System.out.println("=======================>"+bindingResult);

			return "form-project-update";
		}
		
		Optional<Team> optionalTeam = teamRepository.findById(teamId);
		optionalTeam.ifPresent(team -> {

			team.addProject(theProject);
			teamRepository.saveAndFlush(team);

		});

		
		return "redirect:/home";

		
	}
	
	@PostMapping("/saveTask")
	public String saveTask( @Valid @ModelAttribute("task") Task theTask, BindingResult bindingResult, @RequestParam("projectId") int projectId) {
		
		if (bindingResult.hasErrors()) {
			System.out.println("=======================>"+bindingResult);

			return "form-task-update";
		}
		
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		optionalProject.ifPresent(project -> {

			project.addTask(theTask);
			projectRepository.saveAndFlush(project);

			
		});


		return "redirect:/home";

		
	}
	
	@PostMapping("/deleteTeam")
	public String deleteTeam(@RequestParam("teamId") int teamId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Optional<User> optionalUser = userRepository.findByUserName(currentPrincipalName);
		optionalUser.ifPresent(user -> {
			Team team = teamRepository.getOne(teamId);
			System.out.println(team.getId());
			projectRepository.deleteAllByTeamId(teamId);
			user.removeTeam(team);
			teamRepository.delete(team);
});
		
		return "redirect:/home";
	}
	
	@PostMapping("/deleteProject")
	public String deleteProject(@RequestParam("projectId") int projectId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Optional<User> optionalUser = userRepository.findByUserName(currentPrincipalName);
		optionalUser.ifPresent(user -> {
			Project project = projectRepository.getOne(projectId);
			projectRepository.delete(project);

});
		
		return "redirect:/home";
	}
	
	@GetMapping("showFormForUpdateTeam")
	public String showFormForUpdateTeam(@RequestParam("teamId") int teamId, Model theModel) {
		
		Team theTeam = teamRepository.getOne(teamId);
		
		theModel.addAttribute("team", theTeam);
		
		return "form-team-update";
		
	}
	
	@GetMapping("showFormForUpdateProject")
	public String showFormForUpdateProject(@RequestParam("projectId") int projectId, Model theModel) {
		
		Project theProject = projectRepository.getOne(projectId);
		
		theModel.addAttribute("project", theProject);
		
		return "form-project-update";
		
	}
	
	@GetMapping("showFormForUpdateTask")
	public String showFormForUpdateTask(@RequestParam("taskId") int taskId, Model theModel) {
		
		Task theTask = taskRepository.getOne(taskId);
		
		theModel.addAttribute("task", theTask);
		
		return "form-task-update";
		
	}
	
	@PostMapping("/modifyProject")
	public String modifyProject(@Valid @ModelAttribute("project") Project theProject, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			System.out.println("=======================>"+bindingResult);

			return "form-project-update";
		}
		
		int projectId = theProject.getId();
		
		Project project = projectRepository.getOne(projectId);
		
		project.setProjectName(theProject.getProjectName());
		
		
		projectRepository.saveAndFlush(project);
		
		return "redirect:/home";
	}
	
	@PostMapping("/modifyTask")
	public String modifyTask(@Valid @ModelAttribute("task") Task task, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			System.out.println("=======================>"+bindingResult);

			return "form-task-update";
		}
	
		Task taskRepo = taskRepository.getOne(task.getId());
		System.out.println("Task Repo:"+ taskRepo.getProject().getId());
		

		taskRepo.setTaskName(task.getTaskName());
		taskRepo.setTaskState(task.getTaskState());
		
		taskRepository.saveAndFlush(taskRepo);
		
		return "redirect:/home";
	}
	
	@PostMapping("/modifyTeam")
	public String modifyTeam(@Valid @ModelAttribute("team") Team team, BindingResult bindingResult) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//	System.out.println("=======================>"+bindingResult);
		if (bindingResult.hasErrors()) {
			System.out.println("=======================>"+bindingResult);

			return "form-team-update";
		}
		
		Optional<User> optionalUser = userRepository.findByUserName(currentPrincipalName);
		optionalUser.ifPresent(user -> {
			
			//assign the id of the team to be updated 
			int currentTeamId = team.getId();
			
			//Get the team of the selected id from the database 
			Team teamRepo = teamRepository.getOne(currentTeamId);
			
			//Get the index of the current team from the user's list of teams
			int indexOfCurrentTeam = user.getTeams().indexOf(teamRepo);

			//update the team name in the user's team list 
			user.getTeams().set(indexOfCurrentTeam, team);
			
			//update the user
			userRepository.saveAndFlush(user);
		
});


		
		return "redirect:/home";
	}
	
	@PostMapping("deleteTask")
	public String deleteTask(@ModelAttribute("task") Task task, @RequestParam("taskId") int taskId) {
		Task repoTask = taskRepository.getOne(taskId);
		taskRepository.delete(repoTask);
		System.out.println("Task deleted");
		return "redirect:/home";
	}
	
	@PostMapping("registerUser")
	public ModelAndView registerUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, WebRequest request, Errors errors) {

//		
//		//		
		User registered = new User();
//		
		if (!bindingResult.hasErrors()) {
			
			System.out.println("User is registered");
			
			registered = createUserAccount(userDto, bindingResult);

		}
		
		//System.out.println("registered"  + registered);
		
		if (registered == null) {
			
			bindingResult.rejectValue("email", "regError");
			
		}
//		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.toString());
			
			return new ModelAndView("form-registration", "user", userDto);
			
		} else {
			return new ModelAndView("redirect:/login"); 
		}
//		
		// Verify if email exists already

			// if yes: send error "Email exists already"

		//User user = new User();

		// else: verify password match
		// if password and confirmation password match : register user
		// else send error: "passwords do not match"
//		System.out.println("User email");
//		System.out.println(userDto.getEmail());
//		return "form-registration";
		
	}
	
	
	private User createUserAccount(UserDto userDto, BindingResult bindingResult)  {

		User registered = null;
		
		try {
			registered = userService.registerNewUserAccount(userDto);
		} 
		catch (EmailExistsException e) {
			return null;
		} 
		
		
		return registered;
	}

}
