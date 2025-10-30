package cn.fengp.basic.module.pay.api.wallet;

import cn.hutool.core.lang.Assert;
import cn.fengp.basic.framework.common.util.object.BeanUtils;
import cn.fengp.basic.module.pay.api.wallet.dto.PayWalletAddBalanceReqDTO;
import cn.fengp.basic.module.pay.api.wallet.dto.PayWalletRespDTO;
import cn.fengp.basic.module.pay.dal.dataobject.wallet.PayWalletDO;
import cn.fengp.basic.module.pay.enums.wallet.PayWalletBizTypeEnum;
import cn.fengp.basic.module.pay.service.wallet.PayWalletService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 钱包 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class PayWalletApiImpl implements PayWalletApi {

    @Resource
    private PayWalletService payWalletService;

    @Override
    public void addWalletBalance(PayWalletAddBalanceReqDTO reqDTO) {
        // 创建或获取钱包
        PayWalletDO wallet = payWalletService.getOrCreateWallet(reqDTO.getUserId(), reqDTO.getUserType());
        Assert.notNull(wallet, "钱包({}/{})不存在", reqDTO.getUserId(), reqDTO.getUserType());

        // 增加余额
        PayWalletBizTypeEnum bizType = PayWalletBizTypeEnum.valueOf(reqDTO.getBizType());
        payWalletService.addWalletBalance(wallet.getId(), reqDTO.getBizId(), bizType, reqDTO.getPrice());
    }

    @Override
    public PayWalletRespDTO getOrCreateWallet(Long userId, Integer userType) {
        PayWalletDO wallet = payWalletService.getOrCreateWallet(userId, userType);
        return BeanUtils.toBean(wallet, PayWalletRespDTO.class);
    }

}
