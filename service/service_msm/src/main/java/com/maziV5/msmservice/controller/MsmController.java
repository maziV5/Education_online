package com.maziV5.msmservice.controller;

import com.maziV5.msmservice.service.MsmService;
import com.maziV5.msmservice.utils.RandomUtils;
import com.maziV5.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @GetMapping("send/{phone}")
    private R sendMsm(@PathVariable String phone){
        String code = RandomUtils.getRandom();

        boolean flag = msmService.send(code,phone);

        if (flag) {
            return R.ok();
        }
        return R.error();
    }

}
