package com.plateer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SumEvaluationDto {

	private int deliveryCommon;
	private int deliveryBest;
	private int deliveryWorst;
	private int designCommon;
	private int designBest;
	private int designWorst;
	private int sizeCommon;
	private int sizeBest;
	private int sizeWorst;
}
