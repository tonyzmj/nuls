package io.nuls.consensus.handler;

import io.nuls.account.service.intf.AccountService;
import io.nuls.consensus.event.TxGroupEvent;
import io.nuls.consensus.service.cache.BlockCacheService;
import io.nuls.consensus.service.cache.BlockHeaderCacheService;
import io.nuls.core.context.NulsContext;
import io.nuls.event.bus.handler.AbstractNetworkEventHandler;
import io.nuls.ledger.service.intf.LedgerService;
import io.nuls.network.service.NetworkService;

/**
 * @author facjas
 * @date 2017/11/16
 */
public class TxGroupHandler extends AbstractNetworkEventHandler<TxGroupEvent> {
    private BlockHeaderCacheService headerCacheService = BlockHeaderCacheService.getInstance();
    private BlockCacheService blockCacheService = BlockCacheService.getInstance();
    private LedgerService ledgerService = NulsContext.getInstance().getService(LedgerService.class);
    private NetworkService networkService = NulsContext.getInstance().getService(NetworkService.class);
    private AccountService accountService = NulsContext.getInstance().getService(AccountService.class);

    @Override
    public void onEvent(TxGroupEvent event, String fromId) {

        //todo 重新处理，获取header应该改为获取smallblock
//        BlockHeader header = headerCacheService.getHeader(event.getEventBody().getBlockHash().getDigestHex());
//        if (header == null) {
//            return;
//        }
//        Block block = new Block();
//        block.setHeader(header);
//        List<Transaction> txs = new ArrayList<>();
//        for (NulsDigestData txHash : header.getTxHashList()) {
//            Transaction tx = ledgerService.getTxFromCache(txHash.getDigestHex());
//            txs.add(tx);
//        }
//        block.setTxs(txs);
//        ValidateResult<RedPunishData> vResult = block.verify();
//        if (null==vResult||vResult.isFailed()) {
//            networkService.removePeer(fromId);
//            if (vResult.getLevel() == SeverityLevelEnum.FLAGRANT) {
//                RedPunishData data = vResult.getObject();
//                ConsensusMeetingRunner.putPunishData(data);
//            }
//            return;
//        }
//        blockCacheService.cacheBlock(block);
    }
}