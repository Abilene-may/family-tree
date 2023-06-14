package com.example.familytree.service.impl;


import com.example.familytree.commons.Constant;
import com.example.familytree.domain.EventManagement;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.repository.EventManagementRepository;
import com.example.familytree.service.EventManagementService;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 *Service xử lý logic quản lý sự kiện
 *
 * @author nga
 * @since 14/06/2023
 */

@Service
@Slf4j
public class EventManagementServiceImpl implements EventManagementService {
  private final EventManagementRepository eventManagementRepository;

  public EventManagementServiceImpl(
      @Lazy EventManagementRepository eventManagementRepository ){
    super();
    this.eventManagementRepository = eventManagementRepository;
  }

  /**
   * Thêm mới một sự kiện
   *
   * @param eventManagement
   * @return
   * @throws FamilyTreeException
   * @author nga
   * @since 14/06/2023
   */
  @Override
  public EventManagement create(EventManagement eventManagement) throws FamilyTreeException {
    if(eventManagement.getEventDate() != null){
      Integer year = eventManagement.getEventDate().getYear();
      eventManagement.setYear(year);
    } else {
      throw new FamilyTreeException(
          ExceptionUtils.EVENT_DATE_IS_NOT_NULL,
          ExceptionUtils.messages.get(ExceptionUtils.EVENT_DATE_IS_NOT_NULL));
    }
    var status = this.checkStatus(eventManagement.getEventDate());
    eventManagement.setStatus(status);
    eventManagementRepository.save(eventManagement);
    return eventManagement;
  }

  /**
   * Sửa một sự kiện
   *
   * @param eventManagement
   * @throws FamilyTreeException
   */
  @Override
  public void update(EventManagement eventManagement) throws FamilyTreeException {
    var management = this.getById(eventManagement.getId());
    if( eventManagement.getEventDate() != null){
      Integer year = eventManagement.getEventDate().getYear();
      eventManagement.setYear(year);
    } else {
      throw new FamilyTreeException(
          ExceptionUtils.EVENT_DATE_IS_NOT_NULL,
          ExceptionUtils.messages.get(ExceptionUtils.EVENT_DATE_IS_NOT_NULL));
    }
    var status = this.checkStatus(eventManagement.getEventDate());
    eventManagement.setStatus(status);
    eventManagementRepository.save(eventManagement);
  }

  /**
   * check ngày để thiết lập Trạng thái
   * @param eventDate
   * @return
   *
   */
  public String checkStatus(LocalDate eventDate){
    LocalDate today = LocalDate.now();
    // check hôm nay > ngày tổ chức thì status = Đã đóng
    if (today.isAfter(eventDate)){
      return Constant.DA_DONG;
    }
    // check hôm nay < ngày tổ chức thì status = Sắp diễn ra
    if (today.isBefore(eventDate)){
      return Constant.SAP_DIEN_RA;
    }
    return Constant.DANG_DIEN_RA;
  }
  /**
   * Xóa một sự kiện
   *
   * @param id
   * @throws FamilyTreeException
   */
  @Override
  public void delete(Long id) throws FamilyTreeException {

  }

  /**
   * Xem DS các sự kiện
   *
   * @return
   * @throws FamilyTreeException
   */
  @Override
  public List<EventManagement> getAll() throws FamilyTreeException {
    var eventManagements = eventManagementRepository.findAll();
    return eventManagements;
  }

  /**
   * Xem chi tiết một sự kiện
   *
   * @param id của sự kiện
   * @return
   * @throws FamilyTreeException
   */
  @Override
  public EventManagement getById(Long id) throws FamilyTreeException {
    var eventManagementOptional = eventManagementRepository.findById(id);
    if (eventManagementOptional.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.ID_IS_NOT_EXIST));
    }
    return eventManagementOptional.get();
  }
}
