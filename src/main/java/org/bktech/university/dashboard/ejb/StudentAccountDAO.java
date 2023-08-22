package org.bktech.university.dashboard.ejb;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;

import org.bktech.university.dashboard.models.AcademicProgram;
import org.bktech.university.dashboard.models.DegreeProgram;
import org.bktech.university.dashboard.models.Faculty;
import org.bktech.university.dashboard.models.Institution;
import org.bktech.university.dashboard.models.InstitutionCalender;
import org.bktech.university.dashboard.models.PaymentPurpose;
import org.bktech.university.dashboard.models.Student;
import org.bktech.university.dashboard.models.StudyProgramType;
import org.bktech.university.dashboard.models.UserAccount;


@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class StudentAccountDAO implements StudentAccountService{
	
	private EntityManager em = Persistence.createEntityManagerFactory("UniversityFees").createEntityManager();

	public StudentAccountDAO(){}
	
	
	public Faculty getFaculty(String facultyName, Long institutionId)
	{
		try
		{
			return em.createNamedQuery("getFaculty", Faculty.class)
			.setParameter("facultyName", facultyName)
			.setParameter("institutionId", institutionId)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
	}
	
	public Faculty getFacultyByName(String facultyName)
	{
		try
		{
			return em.createNamedQuery("getFacultyByName", Faculty.class)
			.setParameter("facultyName", facultyName)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
	
		
		
	}

	
	public DegreeProgram getDegreeProgram(String degreeName, Long institutionId)
	{
		try
		{
			return em.createNamedQuery("getDegreeProgram", DegreeProgram.class)
			.setParameter("degreeName", degreeName)
			.setParameter("institutionId", institutionId)
			.setMaxResults(1)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
	
	}
	
	public void registerDegreeProgram(DegreeProgram degreeProgram)
	{
		if(!em.getTransaction().isActive())
		em.getTransaction().begin();
		em.persist(degreeProgram);
		em.getTransaction().commit();
	}
	
	public InstitutionCalender getInstitutionCalender(String academicYear, Long institutionId)
	{
		try
		{
			return em.createNamedQuery("getInstitutionCalender", InstitutionCalender.class)
			.setParameter("academicYear", academicYear)
			.setParameter("institutionId", institutionId)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
	}

	public StudyProgramType getStudyProgramType(String section)
	{
		try
		{
			return em.createNamedQuery("getSection", StudyProgramType.class)
			.setParameter("section", "%"+section+"%")
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
	}
	
	public Student getStudent(String regNumber)
	{
		try
		{
			return em.createNamedQuery("getStudent", Student.class)
			.setParameter("reg_number", regNumber)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
	
		
	}
	public Student getStudentByPhone(String phone)
	{
		try
		{
			return em.createNamedQuery("getStudentByPhone", Student.class)
			.setParameter("phone", phone)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
	}
	
	public Student getStudentByNID(String nida)
	{
		try
		{
			return em.createNamedQuery("getStudentByNID", Student.class)
			.setParameter("nida", nida)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
	
	}



	public void registerStudent(Student student)
	{
		if(!em.getTransaction().isActive())
		em.getTransaction().begin();
		em.persist(student);
		em.getTransaction().commit();
	
		
	}
	
	public void registerInvoice(Student student)
	{

		if(!em.getTransaction().isActive())
		em.getTransaction().begin();
		em.persist(student);
		em.getTransaction().commit();
	
		
	
	}
	
	public void registerFaculty(Faculty faculty)
	{


		if(!em.getTransaction().isActive())
		em.getTransaction().begin();
		em.persist(faculty);
		em.getTransaction().commit();
	
		
	
	
	}



    public Institution getInstitution(String accronym)
    {

		try
		{
			return em.createNamedQuery("getInstitution", Institution.class)
			.setParameter("universityAccronym", accronym)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
	
		
	
    	
    }
    
	public AcademicProgram getAcademicProgram(String academicProgramName)
	{
		try
		{
			return em.createNamedQuery("getAcademicProgram", AcademicProgram.class)
			.setParameter("academicProgramName","%"+ academicProgramName+"%")
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
	
		
	
    	
    
		
	}
	
	public DegreeProgram getDegreeProgramByName(String degreeName)
	{
		try
		{
			return em.createNamedQuery("getDegreeProgramByName", DegreeProgram.class)
			.setParameter("degreeName", degreeName)
			.setMaxResults(1)

			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
	
		
	
    	
    
		
	}

	public void updateStudentRecord(Student student, HashMap<String,String>studentRecords, Institution institution)
	{
		Long facultyId = Long.valueOf(0);
		Faculty faculty = null;
		if(!em.getTransaction().isActive())
		em.getTransaction().begin();
	    student.setReg_number(studentRecords.get("regNumber").trim());
		student.setFirst_name(studentRecords.get("firstName"));
		student.setLast_name(studentRecords.get("lastName"));
		if(!studentRecords.get("campus").isEmpty())
		{
			faculty = getFaculty(studentRecords.get("campus").trim(), institution.getId());
			if(faculty != null)
			{
			   student.setFacultyId(faculty);
			   facultyId = faculty.getId();
			}
		}
		if(!studentRecords.get("gender").isEmpty())
		{
			student.setSex(studentRecords.get("gender"));
		}
		if(!studentRecords.get("degreeProgram").isEmpty())
		{
			if(facultyId>0)
			{
				DegreeProgram degreeProgram = new DegreeProgram();
				degreeProgram.setInstitutionId(institution);
				degreeProgram.setFaculty(faculty);
				AcademicProgram academicProgram = getAcademicProgram("undergraduate");
				degreeProgram.setAcademicProgram(academicProgram);
				degreeProgram.setDegreeName(studentRecords.get("degreeProgram"));
				degreeProgram.setDegreeAccronym(studentRecords.get("degreeProgram"));
				degreeProgram.setStatus("Active");
				DegreeProgram degreeProgramObj = getDegreeProgram(studentRecords.get("degreeProgram"), institution.getId());
				if(degreeProgramObj == null)
				{
					registerDegreeProgram(degreeProgram);
					degreeProgramObj = getDegreeProgram(studentRecords.get("degreeProgram"), institution.getId());
                    
					
				}
				
                student.setDegreeProgramId(degreeProgramObj);
			}
			
			
		}
		
		em.getTransaction().commit();
		
		if(!em.getTransaction().isActive())
		em.getTransaction().begin();


		
		if(!studentRecords.get("section").isEmpty())
		{
			StudyProgramType studyProgramType = getStudyProgramType(studentRecords.get("section"));
			if(studyProgramType != null)
			{
				student.setStudyProgramType(studyProgramType);
			}
			else
			{
				 studyProgramType = getStudyProgramType("day");
				 student.setStudyProgramType(studyProgramType);

			}
			
		}
		if(!studentRecords.get("studentClass").isEmpty())
		{
			if(!studentRecords.get("studentClass").equals("-"))
		    student.setStudentClass(studentRecords.get("studentClass"));
		}
		
		if(!studentRecords.get("phoneNumber").isEmpty())
		{
			if(studentRecords.get("phoneNumber").length() == 9)
			{
				Student std = getStudentByPhone("0"+studentRecords.get("phoneNumber"));
				if(std == null)
				{
					student.setPhone("0"+studentRecords.get("phoneNumber"));
				}
			}
			else
			{

				Student std = getStudentByPhone(studentRecords.get("phoneNumber"));
				if(std == null)
				{
					student.setPhone(studentRecords.get("phoneNumber"));
				}
			
				
			}
		}
		
		if(!studentRecords.get("NID").isEmpty())
		{
			if(!studentRecords.get("NID").equals("0"))
			{
				Student std = getStudentByNID(studentRecords.get("NID"));
				if(std == null)
				{
					student.setNida(studentRecords.get("NID"));
				}
			}
		}
		
		if(!studentRecords.get("dateOfBirth").isEmpty())
		{
			String [] splittedDate = studentRecords.get("dateOfBirth").split("\\s+");
			student.setDob(splittedDate[0]);
		}
		

		
		em.getTransaction().commit();
	}

	
	public List<Student> getExpiredInvoices(String isValidStudentRecord)
	{
		    List<Student> listOfStudents = em.createNamedQuery("getExpiredInvoices", Student.class)
		    .setParameter("isValidStudentRecord", isValidStudentRecord)
			.getResultList();
	        
	        return listOfStudents;
			
		
	 }
	
	public int deleteInvoice(Long studentID)
	{
		int result = 0;
		
		try
		{
			em.getTransaction().begin();

			result = em.createNamedQuery("deleteInvoice", Integer.class)
			.setParameter("studentID", studentID)
			.executeUpdate();
			
			em.getTransaction().commit();
			

		}
		catch(Exception e)
		{
			e.printStackTrace();

		}
		
		return result;
	
	}

	public int deleteFacultyRecord(Long facultyID)
	{

		int result = 0;
		
		try
		{
			em.getTransaction().begin();

			result = em.createNamedQuery("deleteFaculty", Integer.class)
			.setParameter("facultyID", facultyID)
			.executeUpdate();
			
			em.getTransaction().commit();
			

		}
		catch(Exception e)
		{
			e.printStackTrace();

		}
		
		return result;
	
	
		
	}

	public Faculty getFacultyByID(Long facultyID)
	{
		try
		{
			return em.createNamedQuery("getFacultyByID", Faculty.class)
			.setParameter("facultyID", facultyID)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		catch(NonUniqueResultException e)
		{

			return null;
		
		}
		
		
	
		
	
	}
	
	public List<Institution> getAllActiveInstitutions(String status)
	{

	    List<Institution> listOfStudents = em.createNamedQuery("getAllActiveInstitutions")
	    .setParameter("status", status)
		.getResultList();
        
        return listOfStudents;
		
	
 
	}
	
	public InstitutionCalender getInstitutionCalenderByInstitutionId(Long institutionId)
	{
		try
		{
			return em.createNamedQuery("getInstitutionCalenderByInstitutionId", InstitutionCalender.class)
			.setParameter("institutionId", institutionId)
			.getSingleResult();
			
		}
		catch(NoResultException e)
		{
			return null;
		}
		
		catch(NonUniqueResultException e)
		{

			return null;
		
		}
		
		
	
		
	
	
		
	}





}
