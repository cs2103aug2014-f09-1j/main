//@author A0126730M
package whatsupnext.structure;

import java.util.Comparator;

public class TaskComparators {

	private static boolean hasEmptyEndTime(Task task) {
		return task.getEndTime() == null || task.getEndTime().isEmpty();
	}

	private static boolean hasEmptyStartTime(Task task) {
		return task.getStartTime() == null || task.getStartTime().isEmpty();
	}
	
	public static class TaskDefaultComparator implements Comparator<Task> {
		@Override
		public int compare(Task taskA, Task taskB) {
			// Floating tasks get sorted alphabetically on description
			// Floating tasks get placed before deadline or time frame tasks
			if (hasEmptyEndTime(taskA) && hasEmptyEndTime(taskB)) {
				return taskA.getDescription().compareToIgnoreCase(taskB.getDescription());
			} else if (hasEmptyEndTime(taskA) && !hasEmptyEndTime(taskB)) {
				return -1;
			} else if (!hasEmptyEndTime(taskA) && hasEmptyEndTime(taskB)) {
				return 1;
			}

			// Deadline tasks get sorted by end time and alphabetically on description
			// Deadline tasks get placed before time frame tasks
			long taskAEndTime = Long.parseLong(taskA.getEndTime());
			long taskBEndTime = Long.parseLong(taskB.getEndTime());

			if (hasEmptyStartTime(taskA) && hasEmptyStartTime(taskB)) {
				if (taskAEndTime < taskBEndTime) {
					return -1;
				} else if (taskAEndTime > taskBEndTime) {
					return 1;
				} else if (taskAEndTime == taskBEndTime) {
					return taskA.getDescription().compareToIgnoreCase(taskB.getDescription());
				}
			} else if (hasEmptyStartTime(taskA) && !hasEmptyStartTime(taskB)) {
				return -1;
			} else if (!hasEmptyStartTime(taskA) && hasEmptyStartTime(taskB)) {
				return 1;
			}

			// Time frame tasks get sorted by end time and by start time and alphabetically on description
			long taskAStartTime = Long.parseLong(taskA.getStartTime());
			long taskBStartTime = Long.parseLong(taskB.getStartTime());

			if (taskAEndTime < taskBEndTime) {
				return -1;
			} else if (taskAEndTime > taskBEndTime) {
				return 1;
			} else {
				if (taskAStartTime < taskBStartTime) {
					return -1;
				} else if (taskAStartTime > taskBStartTime) {
					return 1;
				} else {
					return taskA.getDescription().compareToIgnoreCase(taskB.getDescription());
				}
			}
		}
	}
	
}
