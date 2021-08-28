package com.example.testebsintegration.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PER_BUSINESS_GROUPS", schema = "APPS")
public class PerBusinessGroups {

    @Column(name = "BUSINESS_GROUP_ID", nullable = false)
    @Id
    private Long businessGroupId;

    @Column(name = "ORGANIZATION_ID", nullable = false)
    private Long organizationId;

    @Column(name = "NAME", nullable = false, length = 240)
    private String name;

    @Column(name = "DATE_FROM", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "DATE_TO")
    private LocalDate dateTo;

    @Column(name = "INTERNAL_ADDRESS_LINE", length = 80)
    private String internalAddressLine;

    @Column(name = "LOCATION_ID")
    private Long locationId;

    @Lob
    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "DEFAULT_START_TIME", length = 150)
    private String defaultStartTime;

    @Column(name = "DEFAULT_END_TIME", length = 150)
    private String defaultEndTime;

    @Column(name = "WORKING_HOURS", length = 150)
    private String workingHours;

    @Column(name = "FREQUENCY", length = 150)
    private String frequency;

    @Column(name = "SHORT_NAME", length = 150)
    private String shortName;

    @Column(name = "METHOD_OF_GENERATION_EMP_NUM", length = 150)
    private String methodOfGenerationEmpNum;

    @Column(name = "METHOD_OF_GENERATION_APL_NUM", length = 150)
    private String methodOfGenerationAplNum;

    @Column(name = "GRADE_STRUCTURE", length = 150)
    private String gradeStructure;

    @Column(name = "PEOPLE_GROUP_STRUCTURE", length = 150)
    private String peopleGroupStructure;

    @Column(name = "JOB_STRUCTURE", length = 150)
    private String jobStructure;

    @Column(name = "COST_ALLOCATION_STRUCTURE", length = 150)
    private String costAllocationStructure;

    @Column(name = "POSITION_STRUCTURE", length = 150)
    private String positionStructure;

    @Column(name = "LEGISLATION_CODE", length = 150)
    private String legislationCode;

    @Column(name = "CURRENCY_CODE", length = 150)
    private String currencyCode;

    @Column(name = "SECURITY_GROUP_ID", length = 150)
    private String securityGroupId;

    @Column(name = "ENABLED_FLAG", length = 150)
    private String enabledFlag;

    @Column(name = "COMPETENCE_STRUCTURE", length = 150)
    private String competenceStructure;

    @Column(name = "METHOD_OF_GENERATION_CWK_NUM", length = 150)
    private String methodOfGenerationCwkNum;

    public String getMethodOfGenerationCwkNum() {
        return methodOfGenerationCwkNum;
    }

    public void setMethodOfGenerationCwkNum(String methodOfGenerationCwkNum) {
        this.methodOfGenerationCwkNum = methodOfGenerationCwkNum;
    }

    public String getCompetenceStructure() {
        return competenceStructure;
    }

    public void setCompetenceStructure(String competenceStructure) {
        this.competenceStructure = competenceStructure;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getSecurityGroupId() {
        return securityGroupId;
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getLegislationCode() {
        return legislationCode;
    }

    public void setLegislationCode(String legislationCode) {
        this.legislationCode = legislationCode;
    }

    public String getPositionStructure() {
        return positionStructure;
    }

    public void setPositionStructure(String positionStructure) {
        this.positionStructure = positionStructure;
    }

    public String getCostAllocationStructure() {
        return costAllocationStructure;
    }

    public void setCostAllocationStructure(String costAllocationStructure) {
        this.costAllocationStructure = costAllocationStructure;
    }

    public String getJobStructure() {
        return jobStructure;
    }

    public void setJobStructure(String jobStructure) {
        this.jobStructure = jobStructure;
    }

    public String getPeopleGroupStructure() {
        return peopleGroupStructure;
    }

    public void setPeopleGroupStructure(String peopleGroupStructure) {
        this.peopleGroupStructure = peopleGroupStructure;
    }

    public String getGradeStructure() {
        return gradeStructure;
    }

    public void setGradeStructure(String gradeStructure) {
        this.gradeStructure = gradeStructure;
    }

    public String getMethodOfGenerationAplNum() {
        return methodOfGenerationAplNum;
    }

    public void setMethodOfGenerationAplNum(String methodOfGenerationAplNum) {
        this.methodOfGenerationAplNum = methodOfGenerationAplNum;
    }

    public String getMethodOfGenerationEmpNum() {
        return methodOfGenerationEmpNum;
    }

    public void setMethodOfGenerationEmpNum(String methodOfGenerationEmpNum) {
        this.methodOfGenerationEmpNum = methodOfGenerationEmpNum;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getDefaultEndTime() {
        return defaultEndTime;
    }

    public void setDefaultEndTime(String defaultEndTime) {
        this.defaultEndTime = defaultEndTime;
    }

    public String getDefaultStartTime() {
        return defaultStartTime;
    }

    public void setDefaultStartTime(String defaultStartTime) {
        this.defaultStartTime = defaultStartTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getInternalAddressLine() {
        return internalAddressLine;
    }

    public void setInternalAddressLine(String internalAddressLine) {
        this.internalAddressLine = internalAddressLine;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getBusinessGroupId() {
        return businessGroupId;
    }

    public void setBusinessGroupId(Long businessGroupId) {
        this.businessGroupId = businessGroupId;
    }
}
