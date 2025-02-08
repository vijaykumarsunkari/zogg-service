package com.zogg.zoggservice.controller;

import com.zogg.zoggservice.dtos.ApiResponse;
import com.zogg.zoggservice.dtos.CoinRequestDto;
import com.zogg.zoggservice.dtos.ConvertRequestDto;
import com.zogg.zoggservice.dtos.GameCoinsUpdateRequest;
import com.zogg.zoggservice.dtos.UserWalletDto;
import com.zogg.zoggservice.service.interfaces.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coins")
@RequiredArgsConstructor
public class CoinController {

    private final CoinService coinService;

    @GetMapping("/{userId}")
    public ApiResponse<UserWalletDto> getCoins(@PathVariable Integer userId) {
        return new ApiResponse<>(coinService.getWallet(userId));
    }

    @PostMapping("/update")
    public ApiResponse<String> updateCoins(@RequestBody CoinRequestDto request) {

        coinService.updateCoins(
                request.getUserId(),
                request.getCoinType(),
                request.getTransactionType(),
                request.getAmount());

        return new ApiResponse<>("Coins updated successfully");
    }

    @PostMapping("/convert")
    public ApiResponse<String> convertCoins(@RequestBody ConvertRequestDto request) {

        coinService.convertCoins(
                request.getUserId(),
                request.getFromCoin(),
                request.getToCoin(),
                request.getAmount());
        return new ApiResponse<>("Coins converted successfully");
    }

    @PostMapping("/update/game")
    public ApiResponse<String> gameCoins(@RequestBody GameCoinsUpdateRequest request) {
        coinService.updateGameCoins(request.getUserId(), request.getGoldCoins(), request.getGems());

        return new ApiResponse<>("Game coins updated successfully");
    }
}
