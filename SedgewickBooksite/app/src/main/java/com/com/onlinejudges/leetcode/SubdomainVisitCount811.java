package com.com.onlinejudges.leetcode;

import java.util.*;

public class SubdomainVisitCount811 {
    public static List<String> subdomainVisits(String[] cpdomains) {
        List<String> ret = new ArrayList<>();
        List<String> initDomainList = new ArrayList<>();
        HashMap<String, Integer> domainlistcount = new HashMap<>();
        HashMap<String, Integer> subdomainlistcount = new HashMap<>();
        for (int i=0; i<cpdomains.length; i++) {
            String item = cpdomains[i];
            String[] parts = item.split(" ");
            String domainName = parts[1];
            String count = parts[0];

            domainlistcount.put(domainName, Integer.valueOf(count) + domainlistcount.getOrDefault(domainName, 0));
        }
        for (Map.Entry<String, Integer> domainEntry: domainlistcount.entrySet()) {
            int currentEntryCount = domainlistcount.getOrDefault(domainEntry.getKey(), 0);
            ArrayList<String> sdlist = new ArrayList<>();
            if(domainEntry.getKey().contains(".")) {
                String[] subdomains = domainEntry.getKey().split("\\.", 2);
                String remaining = subdomains[1];
                sdlist.add(remaining);
                while(remaining.contains(".")){
                    String[] sp = remaining.split("\\.", 2);
                    remaining = sp[1];
                    sdlist.add(remaining);
                }
                for(String sd: sdlist) {
                    subdomainlistcount.put(sd, subdomainlistcount.getOrDefault(sd,0) + currentEntryCount);
                }
            }

        }
//        System.out.println(subdomainlistcount);

        for(Map.Entry<String, Integer> entry: subdomainlistcount.entrySet()) {
            domainlistcount.put(entry.getKey(), entry.getValue() + domainlistcount.getOrDefault(entry.getKey(), 0));
        }

//        System.out.println(domainlistcount);
        for(Map.Entry<String, Integer> e2: domainlistcount.entrySet()) {
            ret.add(e2.getValue()+" "+e2.getKey());
        }
        return ret;
    }

    public static void main(String[] args) {
        String[] test = {"5561 cwn.team","4908 lxc.zqy.com","6684 fqv.net","5239 voo.lfn.co","2927 ato.org","8212 gdp.qgx.ca","9523 jfz.org","2442 jbs.hlr.co","3079 vff.kvk.ca","2568 aon.network","601 lzm.ser.us","429 cgl.us","1811 jia.us","7230 kmz.us","8096 kmx.us","7828 zct.gxz.ca","3341 qaw.gtu.net","7004 jkx.org","7037 pzg.fqv.co","1835 mkr.ato.org","6131 wsv.ca","9454 nqi.asz.net","1529 kwf.ser.org","589 ara.network","2269 xuf.org","5406 mup.gxz.com","9310 lfn.ca","3607 wsu.gbq.ca","4982 jia.network","8269 fsw.net","8060 owx.hpn.net","725 mam.syw.team","3123 dhb.cgl.team","2230 gxz.ca","2181 cgw.xuf.team","7384 cgl.co","2508 ojt.wsv.net","6820 nno.irv.network","106 ara.us","7341 wnb.yvs.co","1131 vbo.network","4104 amr.ca","6798 lql.hkt.ca","6362 qph.okv.us","3315 hkt.net","1454 zqk.us","3998 wka.syw.team","2103 fac.ibf.ca","2332 syw.team","9622 akj.jkx.team","9864 mij.gfu.ca","2499 jkg.yvs.net","8955 hel.ato.ca","5336 tjy.ca","6559 dfa.co","5842 kvk.co","2691 zje.thk.ca","1774 ssn.rbg.net","8381 exc.ocf.team","3967 xcm.btv.us","9828 lfn.ca","1941 ghn.xhe.us","5113 qgx.network","8670 wip.us","6984 hlr.us","2240 xfk.gtu.ca","6175 cwn.us","2911 pqm.gjf.ca","6131 mcw.tfm.network","7596 yvs.ca","5067 qzi.team","8480 bki.lmm.network","9236 oep.unn.org","516 asz.org","8390 fkb.kwd.net","2230 oak.zhy.network","112 thk.net","8803 ibf.net","5833 hwu.network","2413 jfz.team","5487 thk.team","9787 wip.team","2793 ljd.zqy.network","5281 hnl.org","1019 pfn.fzx.team","2219 qei.ojr.com","7357 yvr.ksc.network","9555 gwi.unn.team","6822 qst.nva.net","4401 ksc.team","8228 blv.uyy.org","7474 fsw.us","1407 gtt.ulz.net","3025 ltq.us","6083 van.gxj.co","1335 uae.ato.com","2810 tmo.jia.co","9646 kvk.com","4676 kmi.team","7258 tmh.wqv.org"};
        String[] output = {"6881 gdw.net","720 hlr.us","4979 wqv.com","8650 azh.jre.team","9553 iux.org","8801 ara.us","1358 gbq.ca","7025 hkt.ca","6881 epf.gdw.net","9019 hal.team","8682 unn.com","2055 uyy.com","8531 xuf.jkx.ca","5407 qhk.bfo.net","8131 qay.us","9251 kmz.team","5036 qxw.kmx.ca","3012 gjf.net","4544 amr.team","5726 xuf.network","4036 ksc.ca","1050 gxz.org","7566 gjf.network","6752 ato.us","2222 plj.ytn.co","4169 lkl.cwn.com","866 sur.tjy.ca","4439 htq.com","4169 cwn.com","7131 ojr.net","7717 okv.ca","8694 asz.org","4582 xuf.org","5389 epf.org","4008 fqv.com","5036 kmx.ca","9479 iun.hlr.co","8682 whf.unn.com","1998 ltq.org","7746 yls.org","3576 ajl.ca","2377 jqz.mkw.org","2612 kmz.org","9251 miu.kmz.team","5128 ibf.co","2750 arz.org","1947 ato.com","2377 mkw.org","8966 zfz.org","3923 zqy.org","8650 jre.team","9679 umx.dfa.ca","1050 uyf.gxz.org","8188 dfa.com","1945 jre.us","3923 wwo.zqy.org","9895 fsw.us","8918 kmx.team","7504 qzk.rbg.net","7125 btv.com","9906 xpn.ajl.team","65169 ca","6293 jia.net","9323 kvk.com","1551 buf.ca","1895 xvi.ajl.network","3953 jfz.team","2055 nkh.uyy.com","66225 team","46903 co","1311 sci.net","9761 fsw.team","4118 bva.rbg.co","7261 ara.org","1216 kwd.network","9096 ser.org","5004 syf.vbo.us","8459 uyy.network","6385 tzz.fzx.co","9699 gtu.us","90008 com","6066 gqs.co","2223 lux.zqk.team","6632 hax.wip.net","2222 ytn.co","5981 tad.lfn.com","6385 fzx.co","2470 gjf.ca","4008 cke.fqv.com","272 nei.hpn.org","7060 lmx.czb.org","5981 lfn.com","3377 pjp.qzi.org","7632 roc.hlr.com","866 tjy.ca","8272 hpn.org","53977 net","68822 us","4118 rbg.co","8327 vfo.hnl.us","5263 rbg.network","7717 qhr.okv.ca","8531 jkx.ca","4543 bkn.ajl.net","9895 xow.fsw.us","4045 epf.co","6752 kov.ato.us","7632 hlr.com","2223 zqk.team","3576 avm.ajl.ca","4543 ajl.net","5389 fhm.epf.org","9906 ajl.team","5407 bfo.net","8236 ixi.com","5262 iux.net","6066 qeu.gqs.co","2393 upo.ibf.co","3672 hwu.com","2612 tvj.kmz.org","2205 srp.ca","58359 network","8459 vjx.uyy.network","9548 unn.us","9495 wph.org","3809 kmx.network","8327 hnl.us","1 zhy.net","1463 xhe.ca","2550 zfr.epf.co","5004 vbo.us","1551 shc.buf.ca","9679 dfa.ca","1558 okv.network","9460 ato.co","8107 hkt.network","11610 ajl.network","104201 org","7504 rbg.net","5045 unn.network","9656 hoh.ca","4544 vec.amr.team","720 pgl.hlr.us","9479 hlr.co","7060 czb.org","2205 wrw.srp.ca","3377 qzi.org","9572 zqy.com","6632 wip.net","8236 nxm.ixi.com"};
        String[] expected = {"6798 lql.hkt.ca","6083 gxj.co","3998 wka.syw.team","5406 mup.gxz.com","3967 xcm.btv.us","516 asz.org","601 lzm.ser.us","2103 fac.ibf.ca","9787 wip.team","8381 ocf.team","2810 jia.co","7357 yvr.ksc.network","4908 lxc.zqy.com","2230 oak.zhy.network","8480 lmm.network","6131 wsv.ca","8390 fkb.kwd.net","6820 irv.network","3341 qaw.gtu.net","8228 blv.uyy.org","2181 cgw.xuf.team","1529 ser.org","2499 jkg.yvs.net","6798 hkt.ca","7474 fsw.us","3315 hkt.net","2691 zje.thk.ca","2219 ojr.com","1335 ato.com","4908 zqy.com","2103 ibf.ca","78328 team","10058 gxz.ca","8060 hpn.net","2181 xuf.team","9236 oep.unn.org","9555 gwi.unn.team","1941 xhe.us","7004 jkx.org","1335 uae.ato.com","2911 gjf.ca","9555 unn.team","1407 gtt.ulz.net","54027 network","4762 ato.org","7828 zct.gxz.ca","7596 yvs.ca","71438 net","5239 voo.lfn.co","3967 btv.us","7258 wqv.org","9864 gfu.ca","6684 fqv.net","8269 fsw.net","9523 jfz.org","55606 org","8955 ato.ca","6822 nva.net","19138 lfn.ca","8381 exc.ocf.team","2793 zqy.network","5239 lfn.co","725 mam.syw.team","1774 ssn.rbg.net","601 ser.us","3123 dhb.cgl.team","9622 jkx.team","23514 com","8803 ibf.net","3341 gtu.net","6820 nno.irv.network","50737 co","4401 ksc.team","1774 rbg.net","2810 tmo.jia.co","102823 ca","5067 qzi.team","2568 aon.network","7258 tmh.wqv.org","5842 kvk.co","7037 pzg.fqv.co","7037 fqv.co","3079 kvk.ca","1811 jia.us","9454 asz.net","2442 jbs.hlr.co","6822 qst.nva.net","8228 uyy.org","6362 qph.okv.us","4676 kmi.team","5406 gxz.com","3607 gbq.ca","2508 wsv.net","5561 cwn.team","2240 xfk.gtu.ca","5281 hnl.org","1131 vbo.network","2442 hlr.co","6559 dfa.co","9454 nqi.asz.net","3025 ltq.us","1835 mkr.ato.org","64325 us","8212 gdp.qgx.ca","7357 ksc.network","1019 pfn.fzx.team","9622 akj.jkx.team","9236 unn.org","5833 hwu.network","8670 wip.us","5336 tjy.ca","9864 mij.gfu.ca","106 ara.us","112 thk.net","8096 kmx.us","1454 zqk.us","7055 syw.team","2793 ljd.zqy.network","9646 kvk.com","1407 ulz.net","4104 amr.ca","8060 owx.hpn.net","6175 cwn.us","1941 ghn.xhe.us","7341 wnb.yvs.co","4982 jia.network","5487 thk.team","8390 kwd.net","1019 fzx.team","2230 zhy.network","429 cgl.us","2911 pqm.gjf.ca","2508 ojt.wsv.net","2219 qei.ojr.com","7341 yvs.co","8212 qgx.ca","6083 van.gxj.co","6362 okv.us","6131 mcw.tfm.network","2269 xuf.org","589 ara.network","3079 vff.kvk.ca","3123 cgl.team","8480 bki.lmm.network","6984 hlr.us","1529 kwf.ser.org","2691 thk.ca","2499 yvs.net","3607 wsu.gbq.ca","5113 qgx.network","7384 cgl.co","7230 kmz.us","2240 gtu.ca","2413 jfz.team","8955 hel.ato.ca","6131 tfm.network"};
        List<String> ans = subdomainVisits(test);
        System.out.println(Arrays.toString(test));

        Set<String> outputset = new TreeSet<String>(ans);
        Set<String> expectedset = new TreeSet<String>(Arrays.asList(expected));

        System.out.println(outputset.equals(expectedset));

        System.out.println(outputset);
        System.out.println(expectedset);

        String[] test2 = {"5561 cwn.team","4908 lxc.zqy.com","6684 fqv.net","5239 voo.lfn.co","2927 ato.org","8212 gdp.qgx.ca","9523 jfz.org","2442 jbs.hlr.co","3079 vff.kvk.ca","2568 aon.network","601 lzm.ser.us","429 cgl.us","1811 jia.us","7230 kmz.us","8096 kmx.us","7828 zct.gxz.ca","3341 qaw.gtu.net","7004 jkx.org","7037 pzg.fqv.co","1835 mkr.ato.org","6131 wsv.ca","9454 nqi.asz.net","1529 kwf.ser.org","589 ara.network","2269 xuf.org","5406 mup.gxz.com","9310 lfn.ca","3607 wsu.gbq.ca","4982 jia.network","8269 fsw.net","8060 owx.hpn.net","725 mam.syw.team","3123 dhb.cgl.team","2230 gxz.ca","2181 cgw.xuf.team","7384 cgl.co","2508 ojt.wsv.net","6820 nno.irv.network","106 ara.us","7341 wnb.yvs.co","1131 vbo.network","4104 amr.ca","6798 lql.hkt.ca","6362 qph.okv.us","3315 hkt.net","1454 zqk.us","3998 wka.syw.team","2103 fac.ibf.ca","2332 syw.team","9622 akj.jkx.team","9864 mij.gfu.ca","2499 jkg.yvs.net","8955 hel.ato.ca","5336 tjy.ca","6559 dfa.co","5842 kvk.co","2691 zje.thk.ca","1774 ssn.rbg.net","8381 exc.ocf.team","3967 xcm.btv.us","9828 lfn.ca","1941 ghn.xhe.us","5113 qgx.network","8670 wip.us","6984 hlr.us","2240 xfk.gtu.ca","6175 cwn.us","2911 pqm.gjf.ca","6131 mcw.tfm.network","7596 yvs.ca","5067 qzi.team","8480 bki.lmm.network","9236 oep.unn.org","516 asz.org","8390 fkb.kwd.net","2230 oak.zhy.network","112 thk.net","8803 ibf.net","5833 hwu.network","2413 jfz.team","5487 thk.team","9787 wip.team","2793 ljd.zqy.network","5281 hnl.org","1019 pfn.fzx.team","2219 qei.ojr.com","7357 yvr.ksc.network","9555 gwi.unn.team","6822 qst.nva.net","4401 ksc.team","8228 blv.uyy.org","7474 fsw.us","1407 gtt.ulz.net","3025 ltq.us","6083 van.gxj.co","1335 uae.ato.com","2810 tmo.jia.co","9646 kvk.com","4676 kmi.team","7258 tmh.wqv.org"};
        String[] expected2 = {"6798 lql.hkt.ca","6083 gxj.co","3998 wka.syw.team","5406 mup.gxz.com","3967 xcm.btv.us","516 asz.org","601 lzm.ser.us","2103 fac.ibf.ca","9787 wip.team","8381 ocf.team","2810 jia.co","7357 yvr.ksc.network","4908 lxc.zqy.com","2230 oak.zhy.network","8480 lmm.network","6131 wsv.ca","8390 fkb.kwd.net","6820 irv.network","3341 qaw.gtu.net","8228 blv.uyy.org","2181 cgw.xuf.team","1529 ser.org","2499 jkg.yvs.net","6798 hkt.ca","7474 fsw.us","3315 hkt.net","2691 zje.thk.ca","2219 ojr.com","1335 ato.com","4908 zqy.com","2103 ibf.ca","78328 team","10058 gxz.ca","8060 hpn.net","2181 xuf.team","9236 oep.unn.org","9555 gwi.unn.team","1941 xhe.us","7004 jkx.org","1335 uae.ato.com","2911 gjf.ca","9555 unn.team","1407 gtt.ulz.net","54027 network","4762 ato.org","7828 zct.gxz.ca","7596 yvs.ca","71438 net","5239 voo.lfn.co","3967 btv.us","7258 wqv.org","9864 gfu.ca","6684 fqv.net","8269 fsw.net","9523 jfz.org","55606 org","8955 ato.ca","6822 nva.net","19138 lfn.ca","8381 exc.ocf.team","2793 zqy.network","5239 lfn.co","725 mam.syw.team","1774 ssn.rbg.net","601 ser.us","3123 dhb.cgl.team","9622 jkx.team","23514 com","8803 ibf.net","3341 gtu.net","6820 nno.irv.network","50737 co","4401 ksc.team","1774 rbg.net","2810 tmo.jia.co","102823 ca","5067 qzi.team","2568 aon.network","7258 tmh.wqv.org","5842 kvk.co","7037 pzg.fqv.co","7037 fqv.co","3079 kvk.ca","1811 jia.us","9454 asz.net","2442 jbs.hlr.co","6822 qst.nva.net","8228 uyy.org","6362 qph.okv.us","4676 kmi.team","5406 gxz.com","3607 gbq.ca","2508 wsv.net","5561 cwn.team","2240 xfk.gtu.ca","5281 hnl.org","1131 vbo.network","2442 hlr.co","6559 dfa.co","9454 nqi.asz.net","3025 ltq.us","1835 mkr.ato.org","64325 us","8212 gdp.qgx.ca","7357 ksc.network","1019 pfn.fzx.team","9622 akj.jkx.team","9236 unn.org","5833 hwu.network","8670 wip.us","5336 tjy.ca","9864 mij.gfu.ca","106 ara.us","112 thk.net","8096 kmx.us","1454 zqk.us","7055 syw.team","2793 ljd.zqy.network","9646 kvk.com","1407 ulz.net","4104 amr.ca","8060 owx.hpn.net","6175 cwn.us","1941 ghn.xhe.us","7341 wnb.yvs.co","4982 jia.network","5487 thk.team","8390 kwd.net","1019 fzx.team","2230 zhy.network","429 cgl.us","2911 pqm.gjf.ca","2508 ojt.wsv.net","2219 qei.ojr.com","7341 yvs.co","8212 qgx.ca","6083 van.gxj.co","6362 okv.us","6131 mcw.tfm.network","2269 xuf.org","589 ara.network","3079 vff.kvk.ca","3123 cgl.team","8480 bki.lmm.network","6984 hlr.us","1529 kwf.ser.org","2691 thk.ca","2499 yvs.net","3607 wsu.gbq.ca","5113 qgx.network","7384 cgl.co","7230 kmz.us","2240 gtu.ca","2413 jfz.team","8955 hel.ato.ca","6131 tfm.network"};
        List<String> ans2 = subdomainVisits(test2);
        System.out.println(Arrays.toString(test2));

        Set<String> outputset2 = new TreeSet<String>(ans2);
        Set<String> expectedset2 = new TreeSet<String>(Arrays.asList(expected2));

        System.out.println(outputset.equals(expectedset2));
    }
}
