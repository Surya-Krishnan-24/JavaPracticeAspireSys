package com.example.CollegeCoursesManagement.Service;

import com.example.CollegeCoursesManagement.DAO.*;

import com.example.CollegeCoursesManagement.DTO.*;
import com.example.CollegeCoursesManagement.Model.*;

import lombok.RequiredArgsConstructor;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

   
    private final DepartmentRepo departmentRepo;

   
    private final UserRepo userRepo;

    
    private final InstructorRepo instructorRepo;

  
    private final StudentRepo studentRepo;


    private final PasswordEncoder passwordEncoder;

   
    private final CourseRepo courseRepo;
    
   
    private final NotificationService notificationService;

    
    @Override
    public DashboardResponse getDashboardData() {
        DashboardResponse response = new DashboardResponse();

        response.setDepartmentCount(departmentRepo.count());
        response.setInstructorCount(instructorRepo.count());
        response.setStudentCount(studentRepo.count());
        response.setCourseCount(courseRepo.count());

        List<DashboardResponse.DepartmentStats> studentsPerDept = departmentRepo.findAll().stream()
                .map(dept -> {
                    DashboardResponse.DepartmentStats stat = new DashboardResponse.DepartmentStats();
                    stat.setDepartment(dept.getDeptCode());
                    stat.setCount(studentRepo.countByDepartment_DepartmentId(dept.getDepartmentId()));
                    return stat;
                }).collect(Collectors.toList());

        response.setStudentsPerDepartment(studentsPerDept);

        List<Student> allStudents = studentRepo.findAll();
        LocalDateTime now = LocalDateTime.now();
        long year1 = 0, year2 = 0, year3 = 0, year4 = 0;

        for (Student s : allStudents) {
            if (s.getUser() != null && s.getUser().getAccountCreatedAt() != null) {
                int yearsSince = now.getYear() - s.getUser().getAccountCreatedAt().getYear();
                if (yearsSince <= 0) {
					year1++;
				} else if (yearsSince == 1) {
					year2++;
				} else if (yearsSince == 2) {
					year3++;
				} else if (yearsSince == 3) {
					year4++;
				}
            }
        }

        response.setStudentsPerYear(List.of(
                createYearStat("1st Year", year1),
                createYearStat("2nd Year", year2),
                createYearStat("3rd Year", year3),
                createYearStat("4th Year", year4)
        ));
        
        
        List<DashboardResponse.InstructorStats> instructorPerDept = departmentRepo.findAll().stream()
                .map(dept -> {
                    DashboardResponse.InstructorStats stat = new DashboardResponse.InstructorStats();
                    stat.setDepartment(dept.getDeptCode());
                    stat.setCount(instructorRepo.countByDepartment_DepartmentId(dept.getDepartmentId()));
                    return stat;
                }).collect(Collectors.toList());

        response.setInstructorsPerDepartment(instructorPerDept);

        return response;
    }

  
    @Override
    public List<CreateStudentRes> getAllStudents() {
        return studentRepo.findAll().stream()
                .map(this::mapStudentToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CreateStudentRes createStudent(CreateStudentRequest request) {
        Optional<User> existingUser = userRepo.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
			throw new IllegalArgumentException("Username already exists: " + request.getUsername());
		}

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_STUDENT");
        userRepo.save(user);

        Student student = new Student();
        student.setUser(user);
        student.setFullName(request.getFullName());

        if (request.getDepartmentId() != null) {
            Department department = departmentRepo.findById(request.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            student.setDepartment(department);
        }
        
        Instructor instructor = new Instructor();
        studentRepo.save(student);
        notificationService.createAccount(user,student,instructor);
        return mapStudentToResponse(student);
    }

    @Override
    @Transactional
    public CreateStudentRes updateStudent(Long id, UpdateStudentRequest request) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        
        Optional<User> user = userRepo.findById(student.getUser().getUserId());

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            student.setFullName(request.getFullName());
        }

        if (request.getDepartmentId() != null) {
            Department department = departmentRepo.findById(request.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            student.setDepartment(department);
        }

        if (request.getEmail() != null && !request.getEmail().isBlank() && student.getUser() != null) {
            student.getUser().setEmail(request.getEmail());
        }
        
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            user.get().setUsername(request.getUsername());
        }


        if (request.getPassword() != null && !request.getPassword().isBlank() && student.getUser() != null) {
            student.getUser().setPassword(passwordEncoder.encode(request.getPassword()));
        }
        
        userRepo.save(user.get());
        studentRepo.save(student);
        return mapStudentToResponse(student);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        studentRepo.delete(student);
    }

    
    @Override
    public List<CreateInstructorRes> getAllInstructors() {
        return instructorRepo.findAll().stream()
                .map(this::mapInstructorToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CreateInstructorRes createInstructor(CreateInstructorRequest request) {
        Optional<User> existingUser = userRepo.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
			throw new IllegalArgumentException("Username already exists: " + request.getUsername());
		}

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_INSTRUCTOR");
        userRepo.save(user);
        

        Instructor instructor = new Instructor();
        instructor.setUser(user);
        instructor.setFullName(request.getFullName());
        
        if (request.getDepartmentId() != null) {
            Department department = departmentRepo.findById(request.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            instructor.setDepartment(department);
        }
        
        Student student = new Student();
        instructorRepo.save(instructor);
        notificationService.createAccount(user,student,instructor);

        return mapInstructorToResponse(instructor);
    }

    @Override
    @Transactional
    public CreateInstructorRes updateInstructor(Long id, UpdateInstructorRequest request) {
        Instructor instructor = instructorRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        
        Optional<User> user = userRepo.findById(instructor.getUser().getUserId());
        
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            user.get().setUsername(request.getUsername());
        }

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            instructor.setFullName(request.getFullName());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank() && instructor.getUser() != null) {
            instructor.getUser().setEmail(request.getEmail());
        }
        
        if (request.getDepartmentId() != null) {
            Department department = departmentRepo.findById(request.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            instructor.setDepartment(department);
        }

        if (request.getPassword() != null && !request.getPassword().isBlank() && instructor.getUser() != null) {
            instructor.getUser().setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepo.save(user.get());
        instructorRepo.save(instructor);
        return mapInstructorToResponse(instructor);
    }

    @Override
    @Transactional
    public void deleteInstructor(Long id) {
        Instructor instructor = instructorRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));
        instructorRepo.delete(instructor);
    }

   
    @Override
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    @Override
    @Transactional
    public Department createDepartment(CreateDepartmentRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Department name cannot be empty");
        }
        if (request.getDeptCode() == null || request.getDeptCode().isBlank()) {
            throw new IllegalArgumentException("Department code cannot be empty");
        }
        if (departmentRepo.existsByDeptCode(request.getDeptCode())) {
            throw new IllegalArgumentException("Department code already exists: " + request.getDeptCode());
        }

        Department department = new Department();
        department.setName(request.getName());
        department.setDeptCode(request.getDeptCode());
        return departmentRepo.save(department);
    }

    @Override
    @Transactional
    public Department updateDepartment(Long id, CreateDepartmentRequest request) {
        Department department = departmentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        if (request.getName() != null && !request.getName().isBlank()) {
            department.setName(request.getName());
        }
        if (request.getDeptCode() != null && !request.getDeptCode().isBlank()) {
            department.setDeptCode(request.getDeptCode());
        }

        return departmentRepo.save(department);
    }

    @Override
    @Transactional
    public void deleteDepartment(Long id) {
        Department department = departmentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        departmentRepo.delete(department);
    }

    
    private DashboardResponse.YearStats createYearStat(String year, long count) {
        DashboardResponse.YearStats stat = new DashboardResponse.YearStats();
        stat.setYear(year);
        stat.setCount(count);
        return stat;
    }

    private CreateStudentRes mapStudentToResponse(Student s) {
        CreateStudentRes res = new CreateStudentRes();
        res.setStudentId(String.valueOf(s.getStudentId()));
        res.setFullName(s.getFullName());
        res.setUsername(s.getUser() != null ? s.getUser().getUsername() : null);
        res.setEmail(s.getUser() != null ? s.getUser().getEmail() : null);
        res.setDepartmentName(s.getDepartment() != null ? s.getDepartment().getName() : null);
        return res;
    }

    private CreateInstructorRes mapInstructorToResponse(Instructor instructor) {
        CreateInstructorRes res = new CreateInstructorRes();
        res.setInstructorId(String.valueOf(instructor.getInstructorId()));
        res.setFullName(instructor.getFullName());
        res.setDepartmentName(instructor.getDepartment().getName());
        res.setDepartmentId(instructor.getDepartment().getDepartmentId());
        if (instructor.getUser() != null) {
            res.setUsername(instructor.getUser().getUsername());
            res.setEmail(instructor.getUser().getEmail());
        }
        return res;
    }
}
