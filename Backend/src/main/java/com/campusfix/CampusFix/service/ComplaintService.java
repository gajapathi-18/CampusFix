package com.campusfix.CampusFix.service;

import com.campusfix.CampusFix.dto.ComplaintRequest;
import com.campusfix.CampusFix.dto.ComplaintResponse;
import com.campusfix.CampusFix.entity.Complaint;
import com.campusfix.CampusFix.entity.Department;
import com.campusfix.CampusFix.entity.User;
import com.campusfix.CampusFix.enums.ComplaintPriority;
import com.campusfix.CampusFix.enums.ComplaintStatus;
import com.campusfix.CampusFix.repository.ComplaintRepository;
import com.campusfix.CampusFix.repository.DepartmentRepository;
import com.campusfix.CampusFix.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public ComplaintService(
            ComplaintRepository complaintRepository,
            UserRepository userRepository,
            DepartmentRepository departmentRepository) {

        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    public ComplaintResponse submitComplaint(ComplaintRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!user.isActive()) {
            throw new RuntimeException("User account is inactive");
        }

        Complaint complaint = new Complaint();

        complaint.setTitle(request.getTitle().trim());
        complaint.setDescription(request.getDescription().trim());
        complaint.setCategory(request.getCategory().trim());
        complaint.setLocation(request.getLocation().trim());

        complaint.setPriority(
                request.getPriority() == null
                        ? ComplaintPriority.MEDIUM
                        : request.getPriority()
        );

        complaint.setStatus(ComplaintStatus.SUBMITTED);
        complaint.setSubmittedBy(user);

        Department department =
                findDepartment(request.getCategory());

        complaint.setDepartment(department);

        Complaint savedComplaint =
                complaintRepository.save(complaint);

        return new ComplaintResponse(savedComplaint);
    }

    private Department findDepartment(String category) {

        String value = category.toLowerCase();

        String departmentName = null;

        if (value.contains("electrical")
                || value.contains("electric")
                || value.contains("power")
                || value.contains("light")
                || value.contains("fan")) {

            departmentName = "Electrical";

        } else if (value.equals("it")
                || value.contains("information technology")
                || value.contains("network")
                || value.contains("wifi")
                || value.contains("internet")
                || value.contains("computer")) {

            departmentName = "IT";

        } else if (value.contains("plumb")
                || value.contains("water")
                || value.contains("tap")
                || value.contains("leak")) {

            departmentName = "Plumbing";

        } else if (value.contains("civil")
                || value.contains("building")
                || value.contains("door")
                || value.contains("desk")) {

            departmentName = "Civil";
        }

        if (departmentName == null) {
            return null;
        }

        String finalDepartmentName = departmentName;

        return departmentRepository.findAll()
                .stream()
                .filter(department ->
                        department.getName().equalsIgnoreCase(
                                finalDepartmentName))
                .findFirst()
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ComplaintResponse> getAllComplaints() {

        return complaintRepository.findAll()
                .stream()
                .map(ComplaintResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ComplaintResponse> getComplaintsByUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        return complaintRepository.findBySubmittedById(userId)
                .stream()
                .map(ComplaintResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ComplaintResponse getComplaintById(Long id) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found"));

        return new ComplaintResponse(complaint);
    }
}
