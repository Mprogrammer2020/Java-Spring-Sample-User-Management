package com.efx.user.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.efx.user.dto.PrimaryCurrencyDto;

@FeignClient(name="efx-currency-wallet-management")
@RibbonClient(name="efx-currency-wallet-management")
public interface PrimaryCurrencyProxy {

	@PostMapping("EfxCurrency/v1/api/primaryCurrencies")
	  public PrimaryCurrencyDto setPrimaryCurrency
	    (@RequestBody PrimaryCurrencyDto primaryCurrencyDto);
}
