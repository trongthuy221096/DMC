package fa.edu.ktxdmc_be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fa.edu.ktxdmc_be.dto.response.EquipmentResponse;
import fa.edu.ktxdmc_be.model.Equipment;
import fa.edu.ktxdmc_be.service.EquipmentService;

/**
 * Represents an EquipmentController
 * 
 * @author
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("")
public class EquipmentController {
	@Autowired
	private EquipmentService equipmentService;

	/**
	 * Retrieve a paginated list of equipment or perform a search for equipment by
	 * name.
	 *
	 * @param page        The page number for the result (default is 1 if not
	 *                    provided).
	 * @param pageSize    The number of equipment items per page in the result
	 *                    (default is 5 if not provided).
	 * @param valueSearch The value to search for equipment by name (default is an
	 *                    empty string if not provided).
	 * @return ResponseEntity containing a paginated list of EquipmentResponse
	 *         objects if successful, or a "NO_CONTENT" response status if there are
	 *         no equipment items, or an "INTERNAL_SERVER_ERROR" response status if
	 *         an error occurs during processing.
	 */
	@GetMapping("/equipments")
	public ResponseEntity<Page<EquipmentResponse>> getAllRequests(
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
			@RequestParam(name = "valueSearch", defaultValue = "") String valueSearch) {
		try {
			PageRequest pageableService = PageRequest.of(page - 1, pageSize);
			Page<Equipment> servicePage = null;

			if ("".equals(valueSearch)) {
				servicePage = equipmentService.findAll(pageableService);
			} else {
				servicePage = equipmentService.findByEquipmentName(valueSearch, pageableService);
			}

			if (servicePage.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			// Chuyển đổi từ Page<Request> sang Page<RequestDTO>
			Page<EquipmentResponse> requestDtoPage = servicePage.map(EquipmentResponse::new);

			return new ResponseEntity<>(requestDtoPage, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Create a new equipment item.
	 *
	 * @param equipment The Equipment object containing information about the
	 *                  equipment to be created.
	 * @return ResponseEntity containing the newly created Equipment object if
	 *         successful, or an "INTERNAL_SERVER_ERROR" response status if an error
	 *         occurs during creation.
	 */
	@PostMapping("/equipments")
	public ResponseEntity<Equipment> createEquipment(@RequestBody Equipment equipment) {
		try {
			System.out.println(equipment);
			Equipment _equipment = equipmentService
					.save(new Equipment(equipment.getEquipmentName(), equipment.getEquipmentInfomation()));
			return new ResponseEntity<>(_equipment, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Update an existing equipment item by its ID.
	 *
	 * @param id          The unique identifier of the equipment item to be updated.
	 * @param updatedUser The Equipment object containing updated information.
	 * @return ResponseEntity containing the updated Equipment object if successful,
	 *         or an "INTERNAL_SERVER_ERROR" response status if an error occurs
	 *         during the update.
	 */
	@PutMapping("/equipments/{id}")
	public ResponseEntity<Equipment> updateEquipment(@PathVariable("id") Long id, @RequestBody Equipment updatedUser) {
		Equipment equipment = equipmentService.findByEquipmentID(id);
		equipment.setEquipmentName(updatedUser.getEquipmentName());
		equipment.setEquipmentInfomation(updatedUser.getEquipmentInfomation());
		return new ResponseEntity<>(equipmentService.save(equipment), HttpStatus.OK);

	}

	/**
	 * Delete an equipment item by its ID.
	 *
	 * @param id The unique identifier of the equipment item to be deleted.
	 * @return ResponseEntity with a "NO_CONTENT" status if successful deletion, or
	 *         an "INTERNAL_SERVER_ERROR" response status if an error occurs during
	 *         the deletion.
	 */
	@DeleteMapping("/equipments/{id}")
	public ResponseEntity<HttpStatus> deleteEquipment(@PathVariable("id") Long id) {
		try {
			equipmentService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves a list of all equipment.
	 *
	 * @return A ResponseEntity containing a list of EquipmentResponse objects and
	 *         an OK status code if successful, or an INTERNAL_SERVER_ERROR status
	 *         code if an exception occurs.
	 */
	@GetMapping("/listequipment")
	public ResponseEntity<?> getListEquipment() {
		try {
			List<EquipmentResponse> equipmentResponseList = equipmentService.findAll();
			return new ResponseEntity<>(equipmentResponseList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
