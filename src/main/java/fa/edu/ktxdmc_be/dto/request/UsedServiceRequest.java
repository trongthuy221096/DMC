package fa.edu.ktxdmc_be.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import fa.edu.ktxdmc_be.common.Ultis;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsedServiceRequest {
	@NotNull
	private long roomID;

	@NotNull
	private int dien_startNumber;
	
	@NotNull
	private int dien_endNumber;

	@NotNull
	private int nuoc_startNumber;

	@NotNull
	private int nuoc_endNumber;

	@NotNull
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
	private String startDate;

	@NotNull
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
	private String endDate;

	public LocalDate getStartDate() {
		return Ultis.stringToLocalDate(this.startDate);
	}

	public LocalDate getEndDate() {
		return Ultis.stringToLocalDate(this.endDate);
	}
}
