package com.ojas.rpo.security.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.ojas.rpo.security.JsonViews;

@Table(name = "candidate")
@javax.persistence.Entity

public class Candidate implements Entity {

	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "candidate_id", strategy = "com.ojas.rpo.security.util.CandidateIdGenerator")
	@GeneratedValue(generator="candidate_id")
	private Long id;
	@Column
	private Date date;
	@Column
	private String title;
	@Column
	private String candidateStatus;
	@Column
	private String status;
	@Column
	private String street1;
	@Column
	private String street2;
	@Column
	private String candidateSource;
	@Column
	private String altenateEmail;
	@Column
	private String alternateMobile;
	@Column
	private Date submittionDate;

	private String profileDate;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String mobile;
	@Column
	private String email;
	@Column
	private String gender;
	@Column
	private String totalExperience;
	@Column
	private String relevantExperience;
	@Column
	private String currentCTC;
	@Column
	private String expectedCTC;
	@Column
	private String salaryNegotiable;
	@Column
	private String noticePeriod;
	@Column
	private String pincode;
	@Column
	private String city;
	@Column
	private String country;
	@Column
	private String state;
	@Column
	private String willingtoRelocate;
	@Column
	private String skypeID;
	@Column
	private String currentJobType;
	@Column
	private String payRollCompanyName;
	@Column
	private String currentCompanyName;

	private String pancardNumber;
	@Column
	private String certificationscheck;
	@Column
	private String filename;
	@Column
	private String resume;
	@Column
	private byte[] _10thMarkmemo;
	@Column
	private String _12thMarkmemo;
	@Column
	private String highestMarkmemo;
	@Column
	private String latestSalaryslips;
	@Column
	private String salaryCertificates;
	@Column
	private String bankStatements;
	@Column
	private String offeredandReliving;
	@Column
	private String birthCertificate;
	@Column
	private String form16Certificate;
	@Column
	private Date doj;
	@Column
	private String offereStatus;
	@Column
	private String offRejectedReasion;
	@Column
	private String offereLetter;
	@Column
	private String candidateInterviewScheduleStatus;
	@Column
	private String slot1Time;
	@Column
	private Date slot1Date;
	@Column
	private String slot2Time;
	@Column
	private Date slot2Date;
	@Column
	private String slot3Time;
	@Column
	private Date slot3Date;
	
	@Column
	private Date absconded_date;
	

	public Date getAbsconded_date() {
		return absconded_date;
	}

	public void setAbsconded_date(Date absconded_date) {
		this.absconded_date = absconded_date;
	}

	@Column
	private String offeredCtc;
	


	public String getOfferedCtc() {
		return offeredCtc;
	}

	public void setOfferedCtc(String offeredCtc) {
		this.offeredCtc = offeredCtc;
	}

	@Column
	private String onBoardedStatus;

	@Column
	private String onBoardedDate;

	public String getOnBoardedDate() {
		return onBoardedDate;
	}

	public void setOnBoardedDate(String onBoardedDate) {
		this.onBoardedDate = onBoardedDate;
	}

	public String getOnBoardedStatus() {
		return onBoardedStatus;
	}

	public void setOnBoardedStatus(String onBoardedStatus) {
		this.onBoardedStatus = onBoardedStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSlot1Time() {
		return slot1Time;
	}

	public void setSlot1Time(String slot1Time) {
		this.slot1Time = slot1Time;
	}

	public Date getSlot1Date() {
		return slot1Date;
	}

	public void setSlot1Date(Date slot1Date) {
		this.slot1Date = slot1Date;
	}

	public String getSlot2Time() {
		return slot2Time;
	}

	public void setSlot2Time(String slot2Time) {
		this.slot2Time = slot2Time;
	}

	public Date getSlot2Date() {
		return slot2Date;
	}

	public void setSlot2Date(Date slot2Date) {
		this.slot2Date = slot2Date;
	}

	public String getSlot3Time() {
		return slot3Time;
	}

	public void setSlot3Time(String slot3Time) {
		this.slot3Time = slot3Time;
	}

	public Date getSlot3Date() {
		return slot3Date;
	}

	public void setSlot3Date(Date slot3Date) {
		this.slot3Date = slot3Date;
	}

	public String getCandidateInterviewScheduleStatus() {
		return candidateInterviewScheduleStatus;
	}

	public void setCandidateInterviewScheduleStatus(String candidateInterviewScheduleStatus) {
		this.candidateInterviewScheduleStatus = candidateInterviewScheduleStatus;
	}

	@Column
	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column
	@Type(type = "org.hibernate.type.StringClobType")
	private String resumeData;

	@ManyToMany
	@JoinTable(name = "skillcandidate", joinColumns = @JoinColumn(name = "candidate_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "SKILL_ID", referencedColumnName = "ID"))
	private List<Skill> skills = new ArrayList<Skill>();
	@ManyToMany
	@JoinTable(name = "candidateCertificatesMap", joinColumns = @JoinColumn(name = "candidate_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "certificate_ID", referencedColumnName = "ID"))
	private List<Certificate> certificates = new ArrayList<Certificate>();
	@ManyToMany
	@JoinTable(name = "candidateEducationMap", joinColumns = @JoinColumn(name = "candidate_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "education_ID", referencedColumnName = "ID"))
	private List<AddQualification> education = new ArrayList<AddQualification>();

	@ManyToOne
	private User user;

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public void set_10thMarkmemo(byte[] _10thMarkmemo) {
		this._10thMarkmemo = _10thMarkmemo;
	}

	public String getAltenateEmail() {
		return altenateEmail;
	}

	public void setAltenateEmail(String altenateEmail) {
		this.altenateEmail = altenateEmail;
	}

	public String getAlternateMobile() {
		return alternateMobile;
	}

	public void setAlternateMobile(String alternateMobile) {
		this.alternateMobile = alternateMobile;
	}

	public Date getSubmittionDate() {
		return submittionDate;
	}

	public void setSubmittionDate(Date submittionDate) {
		this.submittionDate = submittionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProfileDate() {
		return profileDate;
	}

	public void setProfileDate(String profileDate) {
		this.profileDate = profileDate;
	}

	public String getResumeData() {
		return resumeData;
	}

	public void setResumeData(String resumeData) {
		this.resumeData = resumeData;
	}

	public String getCandidateSource() {
		return candidateSource;
	}

	public void setCandidateSource(String candidateSource) {
		this.candidateSource = candidateSource;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public Candidate() {
		this.date = new Date();
	}

	@JsonView(JsonViews.Admin.class)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonView(JsonViews.User.class)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JsonView(JsonViews.User.class)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonView(JsonViews.User.class)
	public String getCandidateStatus() {
		return candidateStatus;
	}

	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}

	@JsonView(JsonViews.User.class)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonView(JsonViews.User.class)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonView(JsonViews.User.class)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonView(JsonViews.User.class)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonView(JsonViews.User.class)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@JsonView(JsonViews.User.class)
	public String getTotalExperience() {
		return totalExperience;
	}

	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}

	@JsonView(JsonViews.User.class)
	public String getRelevantExperience() {
		return relevantExperience;
	}

	public void setRelevantExperience(String relevantExperience) {
		this.relevantExperience = relevantExperience;
	}

	@JsonView(JsonViews.User.class)
	public String getCurrentCTC() {
		return currentCTC;
	}

	public void setCurrentCTC(String currentCTC) {
		this.currentCTC = currentCTC;
	}

	@JsonView(JsonViews.User.class)
	public String getExpectedCTC() {
		return expectedCTC;
	}

	public void setExpectedCTC(String expectedCTC) {
		this.expectedCTC = expectedCTC;
	}

	@JsonView(JsonViews.User.class)
	public String getSalaryNegotiable() {
		return salaryNegotiable;
	}

	public void setSalaryNegotiable(String salaryNegotiable) {
		this.salaryNegotiable = salaryNegotiable;
	}

	@JsonView(JsonViews.User.class)
	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	@JsonView(JsonViews.User.class)
	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@JsonView(JsonViews.User.class)
	public String getWillingtoRelocate() {
		return willingtoRelocate;
	}

	public void setWillingtoRelocate(String willingtoRelocate) {
		this.willingtoRelocate = willingtoRelocate;
	}

	@JsonView(JsonViews.User.class)
	public String getSkypeID() {
		return skypeID;
	}

	public void setSkypeID(String skypeID) {
		this.skypeID = skypeID;
	}

	@JsonView(JsonViews.User.class)
	public String getCurrentJobType() {
		return currentJobType;
	}

	public void setCurrentJobType(String currentJobType) {
		this.currentJobType = currentJobType;
	}

	@JsonView(JsonViews.User.class)
	public String getPayRollCompanyName() {
		return payRollCompanyName;
	}

	public void setPayRollCompanyName(String payRollCompanyName) {
		this.payRollCompanyName = payRollCompanyName;
	}

	@JsonView(JsonViews.User.class)
	public String getCurrentCompanyName() {
		return currentCompanyName;
	}

	public void setCurrentCompanyName(String currentCompanyName) {
		this.currentCompanyName = currentCompanyName;
	}

	@JsonView(JsonViews.User.class)
	public String getPancardNumber() {
		return pancardNumber;
	}

	public void setPancardNumber(String pancardNumber) {
		this.pancardNumber = pancardNumber;
	}

	@JsonView(JsonViews.User.class)
	public String getCertificationscheck() {
		return certificationscheck;
	}

	public void setCertificationscheck(String certificationscheck) {
		this.certificationscheck = certificationscheck;
	}

	@JsonView(JsonViews.User.class)

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@JsonView(JsonViews.User.class)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@JsonView(JsonViews.User.class)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {

		this.skills = skills;
	}

	public List<Certificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<Certificate> certificates) {
		this.certificates = certificates;
	}

	public List<AddQualification> getEducation() {
		return education;
	}

	public void setEducation(List<AddQualification> education) {
		this.education = education;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public byte[] get_10thMarkmemo() {
		return _10thMarkmemo;
	}

	public String get_12thMarkmemo() {
		return _12thMarkmemo;
	}

	public void set_12thMarkmemo(String _12thMarkmemo) {
		this._12thMarkmemo = _12thMarkmemo;
	}

	public String getHighestMarkmemo() {
		return highestMarkmemo;
	}

	public void setHighestMarkmemo(String highestMarkmemo) {
		this.highestMarkmemo = highestMarkmemo;
	}

	public String getLatestSalaryslips() {
		return latestSalaryslips;
	}

	public void setLatestSalaryslips(String latestSalaryslips) {
		this.latestSalaryslips = latestSalaryslips;
	}

	public String getSalaryCertificates() {
		return salaryCertificates;
	}

	public void setSalaryCertificates(String salaryCertificates) {
		this.salaryCertificates = salaryCertificates;
	}

	public String getBankStatements() {
		return bankStatements;
	}

	public void setBankStatements(String bankStatements) {
		this.bankStatements = bankStatements;
	}

	public String getOfferedandReliving() {
		return offeredandReliving;
	}

	public void setOfferedandReliving(String offeredandReliving) {
		this.offeredandReliving = offeredandReliving;
	}

	public String getBirthCertificate() {
		return birthCertificate;
	}

	public void setBirthCertificate(String birthCertificate) {
		this.birthCertificate = birthCertificate;
	}

	public String getForm16Certificate() {
		return form16Certificate;
	}

	public void setForm16Certificate(String form16Certificate) {
		this.form16Certificate = form16Certificate;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public String getOffereLetter() {
		return offereLetter;
	}

	public void setOffereLetter(String offereLetter) {
		this.offereLetter = offereLetter;
	}

	public String getOffereStatus() {
		return offereStatus;
	}

	public void setOffereStatus(String offereStatus) {
		this.offereStatus = offereStatus;
	}

	public String getOffRejectedReasion() {
		return offRejectedReasion;
	}

	public void setOffRejectedReasion(String offRejectedReasion) {
		this.offRejectedReasion = offRejectedReasion;
	}

}
