package org.bktech.university.dashboard.ejb;

import javax.ejb.Remote;

import org.bktech.university.dashboard.models.Faculty;
import org.bktech.university.dashboard.models.DegreeProgram;
import org.bktech.university.dashboard.models.InstitutionCalender;
import org.bktech.university.dashboard.models.StudyProgramType;
import org.bktech.university.dashboard.models.Student;
import org.bktech.university.dashboard.models.Institution;
import org.bktech.university.dashboard.models.AcademicProgram;

import java.util.HashMap;
import java.util.List;





@Remote
public interface StudentAccountService {
	
	public Faculty getFaculty(String facultyName, Long institutionId);
	public Faculty getFacultyByName(String facultyName);
    public Institution getInstitution(String accronym);
	public DegreeProgram getDegreeProgram(String degreeName, Long institutionId);
	public void registerDegreeProgram(DegreeProgram degreeProgram);
	public InstitutionCalender getInstitutionCalender(String academicYear, Long institutionId);
	public StudyProgramType getStudyProgramType(String section);
	public Student getStudent(String regNumber);
	public Student getStudentByPhone(String phone);
	public Student getStudentByNID(String nida);
	public void registerStudent(Student student);
	public AcademicProgram getAcademicProgram(String academicProgramName);
	public DegreeProgram getDegreeProgramByName(String degreeName);
	public void updateStudentRecord(Student student, HashMap<String,String>studentRecords, Institution institution);
	public List<Student> getExpiredInvoices(String isValidStudentRecord);
	public int deleteInvoice(Long studentID);
	public int deleteFacultyRecord(Long facultyID);
	public Faculty getFacultyByID(Long facultyID);
	public List<Institution> getAllActiveInstitutions(String status);
	public InstitutionCalender getInstitutionCalenderByInstitutionId(Long institutionId);
	public void registerInvoice(Student student);
	public void registerFaculty(Faculty faculty);

	

	
}
