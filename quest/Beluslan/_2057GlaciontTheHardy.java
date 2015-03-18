/**
 *  Project Q is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Project Q is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License along with Project Q. If not, see <http://www.gnu.org/licenses/>.
 */

package quest.Beluslan;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

public class _2057GlaciontTheHardy extends QuestHandler {

	private final static int questId = 2057;
	private final static int[] npc_ids = { 204787, 204784 };
	private final static int[] mob_ids = { 213730, 213788, 213789, 213790, 213791 };

	public _2057GlaciontTheHardy() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerOnEnterZoneMissionEnd(questId);
		qe.registerOnLevelUp(questId);
		qe.registerQuestItem(182204316, questId);
		for (int mob_id : mob_ids) {
			qe.registerQuestNpc(mob_id).addOnKillEvent(questId);
		}
		for (int npc_id : npc_ids) {
			qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
		}
	}

	@Override
	public boolean onZoneMissionEndEvent(QuestEnv env) {
		return defaultOnZoneMissionEndEvent(env, 2056);
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		int[] quests = { 2500, 2056 };
		return defaultOnLvlUpEvent(env, quests, true);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null) {
			return false;
		}

		int var = qs.getQuestVarById(0);
		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc) {
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		}

		if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 204787) { // Chieftain Akagitan
				if (env.getDialog() == DialogAction.QUEST_SELECT) {
					return sendQuestDialog(env, 10002);
				} else if (env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id()) {
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 204787) { // Chieftain Akagitan
				switch (env.getDialog()) {
					case QUEST_SELECT:
						if (var == 0) {
							return sendQuestDialog(env, 1011);
						}
					case SELECT_ACTION_1012: {
						playQuestMovie(env, 246);
						return sendQuestDialog(env, 1012);
					}
					case SETPRO1: {
						playQuestMovie(env, 246);
						return defaultCloseDialog(env, 0, 1);
					}
					default:
						break;
				}
			} else if (targetId == 204784) { // Delris
				switch (env.getDialog()) {
					case QUEST_SELECT:
						if (var == 1) {
							return sendQuestDialog(env, 1352);
						}
					case SETPRO2: {
						playQuestMovie(env, 247);
						return defaultCloseDialog(env, 1, 2, 182204316, 1, 0, 0);
					}
					default:
						break;
				}
			}
		}
		return false;
	}

	@Override
	public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			int targetId = env.getTargetId();
			if (var == 3) {
				int var1 = qs.getQuestVarById(1);
				int var2 = qs.getQuestVarById(2);
				int var3 = qs.getQuestVarById(3);
				int var4 = qs.getQuestVarById(4);
				int var5 = qs.getQuestVarById(5);
				int vars[] = { var1, var2, var3, var4, var5 };
				int allDead = 0;

				if (targetId == 213730 && var1 == 0) { // Glaciont the Hardy
					for (int var0 : vars) {
						if (var0 == 1) {
							allDead++;
						}
					}
					if (allDead == 4) {
						qs.setQuestVar(var);
						qs.setStatus(QuestStatus.REWARD);
						updateQuestStatus(env);
						return true;
					} else {
						changeQuestStep(env, 0, 1, false, 1);
						return true;
					}
				} else if (targetId == 213788 && var2 == 0) { // Frostfist
					for (int var0 : vars) {
						if (var0 == 1) {
							allDead++;
						}
					}
					if (allDead == 4) {
						qs.setQuestVar(var);
						qs.setStatus(QuestStatus.REWARD);
						updateQuestStatus(env);
						return true;
					} else {
						changeQuestStep(env, 0, 1, false, 2);
						return true;
					}
				} else if (targetId == 213789 && var3 == 0) { // Iceback
					for (int var0 : vars) {
						if (var0 == 1) {
							allDead++;
						}
					}
					if (allDead == 4) {
						qs.setQuestVar(var);
						qs.setStatus(QuestStatus.REWARD);
						updateQuestStatus(env);
						return true;
					} else {
						changeQuestStep(env, 0, 1, false, 3);
						return true;
					}
				} else if (targetId == 213790 && var4 == 0) { // Chillblow
					for (int var0 : vars) {
						if (var0 == 1) {
							allDead++;
						}
					}
					if (allDead == 4) {
						qs.setQuestVar(var);
						qs.setStatus(QuestStatus.REWARD);
						updateQuestStatus(env);
						return true;
					} else {
						changeQuestStep(env, 0, 1, false, 4);
						return true;
					}
				} else if (targetId == 213791 && var5 == 0) { // Snowfury
					for (int var0 : vars) {
						if (var0 == 1) {
							allDead++;
						}
					}
					if (allDead == 4) {
						qs.setQuestVar(var);
						qs.setStatus(QuestStatus.REWARD);
						updateQuestStatus(env);
						return true;
					} else {
						changeQuestStep(env, 0, 1, false, 5);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
		Player player = env.getPlayer();
		if (player.isInsideZone(ZoneName.get("DF3_ITEMUSEAREA_Q2057"))) {
			return HandlerResult.fromBoolean(useQuestItem(env, item, 2, 3, false, 248));
		}
		return HandlerResult.FAILED;
	}
}