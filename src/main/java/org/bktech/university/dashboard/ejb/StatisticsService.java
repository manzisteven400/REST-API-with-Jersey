package org.bktech.university.dashboard.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;


@Remote
public interface StatisticsService {
	
	public List<Object[]> getNumberOfStudents();
	public List<Object[]> getNumberOfTransactions();
	public List<Object[]> getTotalPaidAmount();
	public List<Object[]> getTotalPaidAmountByChannel(String channel);
	public Long getNumberOfStudentsByInstitution(Long institutionId);
	public Long getNumberOfTransactionsByInstitution(Long institutionId);
	public Double getTotalPaidAmountByInstitution(Long institutionId);
	public Double getTotalPaidAmountByChannelAndInstitution(String channel, Long institutionId);
	public List<Object[]> getTotalPaidAmountByService(Long institutionId);
	public List<Object[]> getTotalPaidAmountBySubService(Long institutionId);
	public List<Object[]> getNumberOfTransactionsForCaching(String month);
	public Long getNumberOfTransactionsByMonthByInstitution(String month, Long institutionId);






}
