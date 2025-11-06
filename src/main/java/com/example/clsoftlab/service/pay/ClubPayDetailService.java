package com.example.clsoftlab.service.pay;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.clsoftlab.controller.pay.EmployeeWithholdRateController;
import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.dto.pay.ClubItemSearchDto;
import com.example.clsoftlab.dto.pay.ClubPayDetailDto;
import com.example.clsoftlab.dto.pay.ClubPayDetailRequestDto;
import com.example.clsoftlab.entity.ClubPayDetail;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.ClubItemRepository;
import com.example.clsoftlab.repository.pay.ClubPayDetailRepository;
import com.example.clsoftlab.repository.pay.specification.ClubPayDetailSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ClubPayDetailService {


	private final ClubPayDetailRepository clubPayDetailRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ClubItemRepository clubItemRepository;
	private final ModelMapper modelMapper;
	
	public ClubPayDetailService(ClubPayDetailRepository clubPayDetailRepository, EmployeeMasterRepository employeeMasterRepository,
			ClubItemRepository clubItemRepository, ModelMapper modelMapper, EmployeeWithholdRateController employeeWithholdRateController) {
		this.clubPayDetailRepository = clubPayDetailRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.clubItemRepository = clubItemRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 page 조회
	public Page<ClubPayDetailDto> searchClubPayDetail (List<String> empNo, String payYm, List<String> clubCode, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<ClubPayDetail> spec = Specification.not(null);
		
		spec = spec.and(ClubPayDetailSpecs.withEmpNo(empNo))
				.and(ClubPayDetailSpecs.withPayYm(payYm))
				.and(ClubPayDetailSpecs.withClubCode(clubCode));
		
		return clubPayDetailRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, ClubPayDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewDetail (ClubPayDetailRequestDto dto) {
		ClubPayDetail clubPayDetail = modelMapper.map(dto, ClubPayDetail.class);
		clubPayDetail.setEmployee(employeeMasterRepository.getReferenceById(dto.getEmpNo()));
		clubPayDetail.setClubItem(clubItemRepository.getReferenceById(dto.getClubId()));
		
		clubPayDetailRepository.save(clubPayDetail);
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateDetail (ClubPayDetailRequestDto dto) {
		ClubPayDetail clubPayDetail = clubPayDetailRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + dto.getId()));
		
		clubPayDetail.update(dto);
	}
	
	// 상세 정보 조회
	public Optional<ClubPayDetailDto> findById (long id) {
		return clubPayDetailRepository.findById(id)
				.map(i -> modelMapper.map(i, ClubPayDetailDto.class));
	}
	
	// 중복 검사
	public boolean checkOverlap (String empNo, String payYm, Long clubId) {
		return clubPayDetailRepository.existsByEmployee_PernrAndPayYmAndClubItem_Id(empNo, payYm, clubId);
	}
	
	// 검색용 사원 리스트 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return clubPayDetailRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
	
	// 검색용 clubItem 리스트 조회
	public List<ClubItemSearchDto> getClubItemList () {
		return clubPayDetailRepository.getClubItemList().stream()
				.map(i -> modelMapper.map(i, ClubItemSearchDto.class))
				.toList();
	}
}
