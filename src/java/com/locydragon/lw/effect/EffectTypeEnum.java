package com.locydragon.lw.effect;

public enum EffectTypeEnum {
	爆炸(0),中爆炸(1),大爆炸(2),烟花的火星(3),水泡(4),水溅落(5),蓝色颗粒(6),水下特效(7),末地灰色小颗粒(8),
	暴击(9),菱形暴击效果(10),烟雾(11),大烟雾(12),喷溅药水(13),黑色药水效果(14),滞留药水(15),信标(16),
	女巫(17),水(18),岩浆(19),村民生气(20),村民开心(21),菌丝(22),音符盒(23),末影珍珠(24),附魔台(25),烈焰人(26),
	熔岩(27),脚步(28),云(29),红石(30),雪球(31),雪傀儡(32),史莱姆(33),心跳(34),屏障(35),雨(39),未知特效(40),
	远古守卫者(41),末影龙的吐息(42),潜影贝导弹(43),流血(44),横扫(45),沙子(46),不死图腾(47),羊驼攻击(48);
	int f = -1;
	EffectTypeEnum(int t) {
		this.f = t;
	}
	public int getF() {
		return this.f;
	}
}
