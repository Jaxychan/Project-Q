Arita

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.world.zone.ZoneName;

public class _2060RestoringBeluslanObservatory extends QuestHandler {

	private final static int questId = 2060;

	public _2060RestoringBeluslanObservatory() {
		super(questId);
	}

	@Override
	public void register() {
		int[] npcs = { 204701, 204785, 278003, 278088, 700293 };
		qe.registerOnEnterZoneMissionEnd(questId);
		qe.registerOnLevelUp(questId);
		qe.registerQuestItem(182204318, questId);
		qe.registerQuestNpc(700290).addOnKillEvent(questId);
		for (int npc : npcs) {
			qe.registerQuestNpc(npc).addOnTalkEvent(questId);
		}
	}

	@Override
	public boolean onZoneMissionEndEvent(QuestEnv env) {
		return defaultOnZoneMissionEndEvent(env);
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 2500, true);
	}

	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null) {
			return false;
		}
		int var = qs.getQuestVarById(0);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if (qs.getStatus() == QuestStatus.START) {
			switch (targetId) {
				case 204701: { // Hod
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 0) {
								return sendQuestDialog(env, 1011);
							}
						}
						case SETPRO1: {
							return defaultCloseDialog(env, 0, 1);
						}
						default:
							break;
					}
					break;
				}
				case 204785: { // Gwendolin
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 1) {
								return sendQuestDialog(env, 1352);
							} else if (var == 4) {
								if (QuestService.collectItemCheck(env, false)) {
									return sendQuestDialog(env, 2375);
								} else {
									giveQuestItem(env, 182204318, 1);
									return sendQuestDialog(env, 2461);
								}
							}
						}
						case SETPRO2: {
							return defaultCloseDialog(env, 1, 2);
						}
						case SETPRO5: {
							return defaultCloseDialog(env, 4, 5, 0, 0, 182204319, 1);
						}
						case FINISH_DIALOG: {
							if (var == 4) {
								return sendQuestSelectionDialog(env);
							}
						}
						default:
							break;
					}
					break;
				}
				case 278003: { // Hisui
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 2) {
								return sendQuestDialog(env, 1693);
							}
						}
						case SETPRO3: {
							return defaultCloseDialog(env, 2, 3);
						}
						default:
							break;
					}
					break;
				}
				case 278088: { // Glati
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 3) {
								return sendQuestDialog(env, 2034);
							}
						}
						case SETPRO4: {
							return defaultCloseDialog(env, 3, 4, 182204318, 1, 0, 0);
						}
						default:
							break;
					}
					break;
				}
				case 700293: { // Aetheric Field Maintaining Device
					if (dialog == DialogAction.USE_OBJECT) {
						if (var == 8) {
							return useQuestObject(env, 8, 8, true, 0);
						}
					}
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 204701) { // Hod
				if (env.getDialog() == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 10002);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}

	@Override
	public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (var == 4) {
				if (player.isInsideZone(ZoneName.get("AB1_ITEMUSEAREA_Q2060"))) {
					return HandlerResult.fromBoolean(useQuestItem(env, item, 4, 4, false, 182204319, 1, 0));
				}
			}
		}
		return HandlerResult.FAILED;
	}

	@Override
	public boolean onKillEvent(QuestEnv env) {
		return defaultOnKillEvent(env, 700290, 5, 8);
	}
}