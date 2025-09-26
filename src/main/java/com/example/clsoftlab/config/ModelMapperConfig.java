package com.example.clsoftlab.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.clsoftlab.dto.pay.CalcOrderDetailDto;
import com.example.clsoftlab.dto.pay.CalcOrderSimpleDetailDto;
import com.example.clsoftlab.entity.CalcOrder;


@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        
        // 1. Converter 정의: CalcOrder 엔티티를 CalcOrderSimpleDetailDto로 변환하는 방법
        Converter<CalcOrder, CalcOrderSimpleDetailDto> toSimpleDtoConverter = ctx -> {
            CalcOrder source = ctx.getSource();
            if (source == null) {
                return null;
            }
            // 원본 엔티티에서 필요한 데이터(itemCode, payItem.itemName)를 직접 뽑아 Simple DTO를 생성
            return new CalcOrderSimpleDetailDto(
                source.getItemCode(),
                source.getPayItem().getItemName() // 이 과정에서 프록시가 초기화됨
            );
        };

        // 2. PropertyMap에 규칙 적용
        PropertyMap<CalcOrder, CalcOrderDetailDto> calcOrderMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                // 기본 itemName 매핑
                map(source.getPayItem().getItemName(), destination.getItemName());
                
                // 'dependsOn' 필드를 매핑할 때는, 위에서 만든 toSimpleDtoConverter를 사용하라고 지정
                using(toSimpleDtoConverter).map(source.getDependsOn(), destination.getDependsOn());
            }
        };

        modelMapper.addMappings(calcOrderMap);
        
        return modelMapper;
	}
}
